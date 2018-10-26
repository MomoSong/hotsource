/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.language='ko';
	config.resize_enabled = false; // 에디터 크기를 조절하지 않음
	config.enterMode=CKEDITOR.ENTER_BR; // 엔터키를 <br> 로 적용함.
	config.shiftEnterMode=CKEDITOR.ENTER_P; // 쉬프트 + 엔터를 <p> 로 적용함.
	config.toolbarCanCollapse=true;
	config.height='460px';
	config.removePlugins='elementspath'; // DOM 출력하지 않음
	
	config.font_defaultLabel='나눔고딕';
	config.font_names="나눔고딕/나눔고딕,Nanum Gothic,ng;굴림/굴림,Gulim;바탕/바탕,Batang; Arial/Arial";
	config.fontSize_sizes='8/8px;9/9px;10/10px;11/11px;12/12px;13/13px; 14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;';
	config.fontSize_defaultLabel='13px';
	// 에디터에 사용할 기능들 정의
	config.toolbar=[ 
		[ 'Source', '-'],
		['Undo', 'Redo' ],
		[ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript'],
		[ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ],
		'/',
		[ 'Styles', 'Format', 'Font', 'FontSize' ],
		[ 'TextColor', 'BGColor' ],
		[ 'Image', 'Flash', 'Link', 'Unlink']
		];
	
	
	config.filebrowserUploadUrl='/board_fileupload.do';
	
};