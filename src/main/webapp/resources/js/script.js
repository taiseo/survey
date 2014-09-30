$(document).ready(function(){
	attach_active_to_nav();
	init_tinymce();
	init_datepicker();
	init_only_number();
	init_underscore_template();
	bind_show_hide();
	bind_show_hide_responses();
	bind_date_order_valid();
});

function attach_active_to_nav(){
	$('.navbar li a').each(function(){
		if(location.href.indexOf($(this).attr('href')) > -1){
			$(this).parents('li').addClass('active');
		}
	});
}

function init_tinymce(){
	if($('.wysiwyg').length > 0){
		tinymce.init({
			content_css : survey.context_path + "/resources/css/editor_style.css",
			selector: '.wysiwyg',
			plugins: ["link", "image"],
			menubar : false,
			toolbar: "bold | link unlink | image | bullist numlist | alignleft aligncenter alignright"
		});
	}
}

function init_datepicker(){
	if($('.datepicker').length > 0){
		$('.datepicker').datepicker({
			language: "kr",
			autoclose: true,
			format: 'yyyy-mm-dd'
		});
	}
}

function init_only_number(){
	$('body').on('blur', '.js-number', function(){
		$(this).val($(this).val().replace(/[^\-0-9]/g, ''));
	});
}

function mynoty(text, option){
	var defaults = {
		layout: 'topRight',
		type: 'success',
		timeout: 2000,
		maxVisible: 10,
		text: text
	};
	var option = $.extend(defaults, option);
	noty(option);
}

function init_underscore_template(){
	_.templateSettings = {
	    interpolate: /\<\@\=(.+?)\@\>/gim,
	    evaluate: /\<\@(.+?)\@\>/gim,
	    escape: /\<\@\-(.+?)\@\>/gim
	};
}

function bind_show_hide(){
	  // 열리는 이벤트를 건다.
	  $('.jquery-on-container').on('click', '.js-open-target', function(e){

	    // a나 submit 버튼에 클래스를 매긴 경우 링크 이동이나 submit을 막는다.
	    e.preventDefault();
	    var target_selector = $(this).data('target');
	    $(target_selector).slideDown();
	    $(this).removeClass('js-open-target').addClass('js-close-target');
	  });

	  /*
	   * 처음엔 .js-close-target 클래스가 DOM에 로드돼 있지 않다.
	   * on 함수를 이용하면 감시하고 있던 요소 안에 정해 준 요소가 등장했을 때 이벤트를 건다.
	   * 이 경우, .jquery-on-container를 감시하고 있다가 .js-close-target가 등장하면
	   * 닫히는 이벤트를 건다. 앞의 열리는 이벤트를 on 함수로 건 이유도 마찬가지다. 
	   * 클래스를 js-open-target으로 했다가 js-close-target으로 했다가 하기 때문이다.
	   */
	  $('.jquery-on-container').on('click', '.js-close-target', function(e){
	    e.preventDefault();
	    var target_selector = $(this).data('target');
	    $(target_selector).slideUp();
	    $(this).removeClass('js-close-target').addClass('js-open-target');
	  })

	  // js를 끈 경우엔 컨텐츠가 보이도록 js로 열 것은 js에서 감춘다.
	  $('.js-open-target').each(function(){
	    var target_selector = $(this).data('target');
	    $(target_selector).hide();
	  });
}

function bind_date_order_valid(){
	$('[name=startDate], [name=endDate]').on('changeDate', function(){
		var startDate = $('[name=startDate]').val();
		var endDate = $('[name=endDate]').val();
		
		if( ! startDate || ! endDate){
			return;
		}
		
		if(startDate > endDate){
			alert('시작일이 종료일보다 뒤입니다.');
			$('[name=endDate]').focus();
			return false;
		}
	});
	
	$('[name=targetStartDate], [name=targetEndDate]').on('changeDate', function(){
		var startDate = $('[name=targetStartDate]').val();
		var endDate = $('[name=targetEndDate]').val();
		
		if( ! startDate || ! endDate){
			return;
		}
		
		if(startDate > endDate){
			alert('시작일이 종료일보다 뒤입니다.');
			$('[name=targetEndDate]').focus();
			return false;
		}
	});
}

function bind_show_hide_responses(){
	$('.js-open-responses').click(function(e){
		e.preventDefault();
		$(this).parent().next().removeClass('limit-height');
		$(this).hide();
		$(this).next().show();
	});
	$('.js-close-responses').click(function(e){
		e.preventDefault();
		$(this).parent().next().addClass('limit-height');
		$(this).hide();
		$(this).prev().show();
	});
}