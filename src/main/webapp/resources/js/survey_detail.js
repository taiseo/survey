$(document).ready(function(){
	bind_add_question();
	bind_add_response_item();
	bind_sortable();
	bind_remove();
});

function bind_add_question(){
	$('.js-add-question').click(function(){
		var title = '<h2>' + $(this).val() + '</h2>';
		var template_id = $(this).data('template-id');
		$('.js-questions-area').append($($('#' + template_id).html()).prepend(title));
		
		bind_sortable_response_items();
	});
}

function bind_add_response_item(){
	$('.js-questions-area').on('click', '.js-add-reponse-item', function(){
		var template_id = $(this).data('template-id');
		$(this)
			.parents('.question')
			.find('.js-response-items-area')
			.append($('#' + template_id).html());
	});
	
}

function bind_sortable(){
	$('.js-questions-area').sortable({
		// TODO DB 순서 변경 로직 추가
		handle: '.js-move-question'
	});
}

function bind_sortable_response_items(){
	$('.js-questions-area .js-response-items-area').sortable({
		handle: '.js-move-response-item'
	});
}

function bind_remove(){
	$('.js-questions-area').on('click', '.js-remove-question', function(){
		// TODO DB 삭제 로직 추가
		$(this).parents('.question').remove();
	});
}