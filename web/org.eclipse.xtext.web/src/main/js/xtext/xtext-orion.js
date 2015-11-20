/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

/*
 * Use `createEditor(options)` to create an Xtext editor. You can specify options either
 * through the function parameter or through `data-editor-x` attributes, where x is an
 * option name with camelCase converted to hyphen-separated.
 * The following options are available:
 *
 * baseUrl = "/" {String}
 *     The path segment where the Xtext service is found; see serviceUrl option.
 * contentAssistCharTriggers {String}
 *     Characters that invoke the content assist service when typed (see Orion documentation).
 * contentAssistExcludedStyles {String}
 *     Excluded styles for character triggers (see Orion documentation).
 * contentType {String}
 *     The content type included in requests to the Xtext server.
 * dirtyElement {String | DOMElement}
 *     An element into which the dirty status class is written when the editor is marked dirty;
 *     it can be either a DOM element or an ID for a DOM element.
 * dirtyStatusClass = 'dirty' {String}
 *     A CSS class name written into the dirtyElement when the editor is marked dirty.
 * document {Document}
 *     The document; if not specified, the global document is used.
 * enableContentAssistService = true {Boolean}
 *     Whether content assist should be enabled.
 * enableFormattingAction = false {Boolean}
 *     Whether the formatting action should be bound to the standard keystroke ctrl+shift+f / cmd+shift+f.
 * enableFormattingService = true {Boolean}
 *     Whether text formatting should be enabled.
 * enableGeneratorService = true {Boolean}
 *     Whether code generation should be enabled (must be triggered through JavaScript code).
 * enableHoverService = true {Boolean}
 *     Whether mouse hover information should be enabled.
 * enableHighlightingService = true {Boolean}
 *     Whether semantic highlighting (computed on the server) should be enabled.
 * enableOccurrencesService = true {Boolean}
 *     Whether marking occurrences should be enabled.
 * enableSaveAction = false {Boolean}
 *     Whether the save action should be bound to the standard keystroke ctrl+s / cmd+s.
 * enableValidationService = true {Boolean}
 *     Whether validation should be enabled.
 * loadFromServer = true {Boolean}
 *     Whether to load the editor content from the server.
 * mouseHoverDelay = 500 {Number}
 *     The number of milliseconds to wait after a mouse hover before the information tooltip is displayed.
 * parent = 'xtext-editor' {String | DOMElement}
 *     The parent element for the view; it can be either a DOM element or an ID for a DOM element.
 * parentClass = 'xtext-editor' {String}
 *     If the 'parent' option is not given, this option is used to find elements that match the given class name.
 * randomContentType = true {Boolean}
 *     Whether to generate a random content type to avoid clashes between multiple editor instances.
 * resourceId {String}
 *     The identifier of the resource displayed in the text editor; this option is sent to the server to
 *     communicate required information on the respective resource.
 * selectionUpdateDelay = 550 {Number}
 *     The number of milliseconds to wait after a selection change before Xtext services are invoked.
 * sendFullText = false {Boolean}
 *     Whether the full text shall be sent to the server with each request; use this if you want
 *     the server to run in stateless mode. If the option is inactive, the server state is updated regularly.
 * serviceUrl {String}
 *     The URL of the Xtext servlet; if no value is given, it is constructed using the baseUrl option in the form
 *     {location.protocol}//{location.host}{baseUrl}xtext-service
 * showErrorDialogs = false {Boolean}
 *     Whether errors should be displayed in popup dialogs.
 * syntaxDefinition {String}
 *     A path to a JS file defining an Orion syntax definition; if no path is given, it is built from
 *     the 'xtextLang' option in the form 'xtext-resources/{xtextLang}-syntax'. Set this option to 'none' to
 *     disable syntax highlighting.
 * textUpdateDelay = 500 {Number}
 *     The number of milliseconds to wait after a text change before Xtext services are invoked.
 * xtextLang {String}
 *     The language name (usually the file extension configured for the language).
 */
define([
	'jquery',
	'orion/Deferred',
	'orion/codeEdit',
	'orion/keyBinding',
	'orion/editor/annotations',
	'xtext/compatibility',
	'xtext/ServiceBuilder',
	'xtext/OrionEditorContext'
], function(jQuery, OrionDeferred, CodeEdit, mKeyBinding, mAnnotations, compatibility,
		ServiceBuilder, EditorContext) {
	
	var exports = {};
	
	/**
	 * Create one or more Xtext editor instances configured with the given options.
	 * The return value is a Deferred object that eventually resolves to either an Orion
	 * editor viewer or an array of Orion editor viewers.
	 */
	exports.createEditor = function(options) {
		if (!options)
			options = {};
		
		var query;
		if (jQuery.type(options.parent) === 'string') {
			query = jQuery('#' + options.parent, options.document);
		} else if (options.parent) {
			query = jQuery(options.parent);
		} else if (jQuery.type(options.parentClass) === 'string') {
			query = jQuery('.' + options.parent, options.document);
		} else {
			query = jQuery('#xtext-editor', options.document);
			if (query.length == 0)
				query = jQuery('.xtext-editor', options.document);
		}
		
		var deferred = jQuery.Deferred();
		var editorViewers = [];
		var finishedViewers = 0;
		var codeEdit = new CodeEdit();
		query.each(function(index, parent) {
			var editorOptions = ServiceBuilder.mergeParentOptions(parent, options);
			var content = jQuery(parent).text();
			
			codeEdit.create({parent: parent}).then(function(editorViewer) {
				exports.createServices(editorViewer, editorOptions).done(function(xtextServices) {
					if (!xtextServices.options.loadFromServer)
						editorViewer.setContents(content, xtextServices.contentType);
					editorViewers[index] = editorViewer;
					finishedViewers++;
					if (finishedViewers == query.length) {
						if (finishedViewers == 1)
							deferred.resolve(editorViewer);
						else
							deferred.resolve(editorViewers);
					}
				});
			}, function(error) {
				deferred.reject(error);
			});
			
		});
		if (query.length == 0)
			deferred.reject();
		return deferred.promise();
	}
	
	function OrionServiceBuilder(editorViewer, xtextServices) {
		this.viewer = editorViewer;
		ServiceBuilder.call(this, xtextServices);
	}
	OrionServiceBuilder.prototype = new ServiceBuilder();
	
	/**
	 * Configure Xtext services for the given editor. The editor does not have to be created
	 * with createEditor(options).
	 */
	exports.createServices = function(editorViewer, options) {
		var deferred = jQuery.Deferred();
		if (!options.xtextLang && options.resourceId)
			options.xtextLang = options.resourceId.split(/[?#]/)[0].split('.').pop();
		var contentType = options.contentType;
		if (!contentType && options.xtextLang)
			contentType = 'xtext/' + options.xtextLang;
		else if (!contentType)
			contentType = 'xtext';
		if (options.randomContentType === undefined || options.randomContentType)
			contentType = contentType + '_' + Math.floor(Math.random() * 2147483648).toString(16);
		editorViewer.serviceRegistry.registerService('orion.core.contenttype', {}, {
			contentTypes: [{
				id: contentType,
				extension: [options.xtextLang],
				name: 'Xtext Language',
				'extends': 'text/plain'
			}]
		});
		
		var xtextServices = {
			options: options,
			editorContext: new EditorContext(editorViewer, contentType),
			contentType: contentType,
			_highlightAnnotationTypes: []
		};
		var serviceBuilder = new OrionServiceBuilder(editorViewer, xtextServices);
		
		var syntaxDefinition = options.syntaxDefinition;
		if (syntaxDefinition != 'none' && (jQuery.type(syntaxDefinition) === 'string' || !syntaxDefinition && options.xtextLang)) {
			if (!syntaxDefinition)
				syntaxDefinition = 'xtext-resources/' + options.xtextLang + '-syntax';
			require([syntaxDefinition], function(grammar) {
				if (!grammar.contentTypes)
					grammar.contentTypes = [contentType];
				else if (jQuery.inArray(contentType, grammar.contentTypes) < 0)
					grammar.contentTypes.push(contentType);
				options.syntaxDefinition = grammar;
				serviceBuilder.createServices();
				deferred.resolve(xtextServices);
			});
		} else {
			serviceBuilder.createServices();
			deferred.resolve(xtextServices);
		}

		xtextServices.serviceBuilder = serviceBuilder;
		editorViewer.xtextServices = xtextServices;
		return deferred.promise();
	}
	
	/**
	 * Syntax highlighting (without semantic highlighting).
	 */
	OrionServiceBuilder.prototype.setupSyntaxHighlighting = function() {
		var syntaxDefinition = this.services.options.syntaxDefinition;
		if (syntaxDefinition != 'none') {
			this.viewer.serviceRegistry.registerService('orion.edit.highlighter', {}, syntaxDefinition);
		}
	}
	
	/**
	 * Document update service.
	 */
	OrionServiceBuilder.prototype.setupUpdateService = function(refreshDocument) {
		var services = this.services;
		var editorContext = services.editorContext;
		var textUpdateDelay = services.options.textUpdateDelay;
		if (!textUpdateDelay)
			textUpdateDelay = 500;
		function modelChangeListener(event) {
			// The first event is triggered by editorViewer.setContents(..)
			if (editorContext._xtext_init)
				editorContext._xtext_init = false;
			else
				editorContext.setDirty(true);
			if (editorContext._modelChangeTimeout)
				clearTimeout(editorContext._modelChangeTimeout);
			editorContext._modelChangeTimeout = setTimeout(function() {
				if (services.options.sendFullText)
					refreshDocument();
				else
					services.update();
			}, textUpdateDelay);
		}
		editorContext._xtext_init = true;
		this.viewer.serviceRegistry.registerService('orion.edit.model',
				{onModelChanged: modelChangeListener}, {contentType: [services.contentType]});
	}
	
	/**
	 * Persistence services: load, save, and revert.
	 */
	OrionServiceBuilder.prototype.setupPersistenceServices = function() {
		var services = this.services;
		if (services.options.enableSaveAction) {
			this.viewer.serviceRegistry.registerService('orion.edit.command', {execute: services.saveResource}, {
				name: 'Save Xtext document',
				id: 'xtext.save',
				key: ['s', true],
				contentType: [services.contentType]
			});
		}
	}
	
	/**
	 * Content assist service.
	 */
	OrionServiceBuilder.prototype.setupContentAssistService = function() {
		var services = this.services;
		var options = services.options;
		function computeContentAssist(orionContext, params) {
			ServiceBuilder.mergeOptions(params, options);
			var deferred = new OrionDeferred();
			services.contentAssistService.invoke(services.editorContext, params).done(function(entries) {
				deferred.resolve(entries.map(function(entry) {
					return {
						proposal: entry.proposal,
						prefix: entry.prefix,
						overwrite: true,
						name: (entry.label ? entry.label: entry.proposal),
						description: entry.description,
						additionalEdits: entry.textReplacements,
						positions: entry.editPositions,
						escapePosition: entry.escapePosition
					};
				}));
			}).fail(function() {
				deferred.reject();
			});
			return deferred.promise;
		}
		var editorViewer = this.viewer;
		var serviceInfo = {
			name: 'Xtext content assist service',
			contentType: [services.contentType]
		};
		if (options.contentAssistCharTriggers) {
			serviceInfo.charTriggers = options.contentAssistCharTriggers;
			serviceInfo.excludedStyles = options.contentAssistExcludedStyles;
			if (editorViewer.settings)
				editorViewer.settings.contentAssistAutoTrigger = true;
		}
		editorViewer.serviceRegistry.registerService('orion.edit.contentAssist',
				{computeContentAssist: computeContentAssist}, serviceInfo);
	}
	
	/**
	 * On-demand registers and returns the annotation type with the given name.
	 */
	OrionServiceBuilder.prototype._getHighlightAnnotationType = function(typeName) {
		var type = mAnnotations.AnnotationType.getType(typeName);
		if (type)
			return typeName;
		var properties = {
			title: typeName,
			style: {styleClass: "annotation " + typeName},
			html: "<div class='annotationHTML " + typeName + "'></div>", 
			overviewStyle: {styleClass: "annotationOverview " + typeName}
		};
		properties.rangeStyle = {styleClass: "annotationRange " + typeName};
		var newType = mAnnotations.AnnotationType.registerType(typeName, properties);
		this.services._highlightAnnotationTypes.push(newType);
		this.viewer.editor.getAnnotationStyler().addAnnotationType(newType);
		return newType;
	}
		
	/**
	 * Semantic highlighting service.
	 */
	OrionServiceBuilder.prototype.doHighlighting = function() {
		var editorViewer = this.viewer;
		var services = this.services;
		var self = this;
		services.computeHighlighting().done(function(result) {
			var annotations = [];
			for (var i = 0; i < result.regions.length; ++i) {
				var region = result.regions[i];
				region.styleClasses.forEach(function(styleClass) {
					annotations.push({
						description: '',
						start: region.offset,
						end: region.offset + region.length,
						styleClass: styleClass
					});
				});
			}
			editorViewer.editor.showAnnotations(annotations, services._highlightAnnotationTypes, null, function(annotation) {
				return self._getHighlightAnnotationType(annotation.styleClass, editorViewer, services);
			});
		});
	}
			
	/**
	 * Validation service.
	 */
	OrionServiceBuilder.prototype.doValidation = function() {
		var editorViewer = this.viewer;
		var services = this.services;
		services.validate().done(function(result) {
			editorViewer.editor.showProblems(result.issues.map(function(entry) {
				return {
					description: entry.description,
					start: entry.offset,
					end: entry.offset + entry.length,
					severity: entry.severity
				};
			}));
		});
	}
	
	/**
	 * Hover service.
	 */
	OrionServiceBuilder.prototype.setupHoverService = function() {
		var services = this.services;
		var options = services.options;
		function computeHoverInfo(orionContext, params) {
			var deferred = new OrionDeferred();
			ServiceBuilder.mergeOptions(params, options);
			services.hoverService.invoke(services.editorContext, params).done(function(entry) {
				deferred.resolve({
					content: entry.title + entry.content,
					type: 'html'
				});
			}).fail(function() {
				deferred.resolve(null);
			});
			return deferred.promise;
		}
		this.viewer.serviceRegistry.registerService('orion.edit.hover',
				{computeHoverInfo: computeHoverInfo},
				{name: 'Xtext hover service', contentType: [services.contentType]});
	}
	
	/**
	 * Occurrences service.
	 */
	OrionServiceBuilder.prototype.setupOccurrencesService = function() {
		var services = this.services;
		var options = services.options;
		function computeOccurrences(orionContext, params) {
			var deferred = new OrionDeferred();
			ServiceBuilder.mergeOptions(params, options);
			services.occurrencesService.invoke(services.editorContext, params).done(function(occurrencesResult) {
				var readAnnotations = occurrencesResult.readRegions.map(function(region) {
					return {
						start: region.offset,
						end: region.offset + region.length,
						readAccess: true
					};
				});
				var writeAnnotations = occurrencesResult.writeRegions.map(function(region) {
					return {
						start: region.offset,
						end: region.offset + region.length,
						readAccess: false
					};
				});
				deferred.resolve(readAnnotations.concat(writeAnnotations));
			}).fail(function() {
				deferred.resolve({});
			});
			return deferred.promise;
		}
		var editorViewer = this.viewer;
		if (editorViewer.settings)
			editorViewer.settings.showOccurrences = true;
		editorViewer.serviceRegistry.registerService('orion.edit.occurrences',
				{computeOccurrences: computeOccurrences}, {contentType: [services.contentType]});
	}
	
	/**
	 * Formatting service.
	 */
	OrionServiceBuilder.prototype.setupFormattingService = function() {
		var services = this.services;
		var options = services.options;
		if (options.enableFormattingAction) {
			function execute(orionContext, params) {
				var deferred = new OrionDeferred();
				ServiceBuilder.mergeOptions(params, options);
				services.formattingService.invoke(services.editorContext, params).done(function(result) {
					deferred.resolve();
				}).fail(function() {
					deferred.reject();
				});
				return deferred.promise;
			}
			this.viewer.serviceRegistry.registerService('orion.edit.command', {execute: execute}, {
				name: 'Xtext formatting service',
				id: 'xtext.formatter',
				key: ['f', true, true],
				contentType: [services.contentType]
			});
		}
	}
	
	return exports;
});

