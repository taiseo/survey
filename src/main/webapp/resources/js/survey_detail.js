$(document).ready(function(){
	bind_add_question();
	bind_add_response_item();
	bind_sortable();
	bind_remove();
	bind_save();
});

function bind_add_question(){
	$('.js-add-question').click(function(){
		var title = '<h2>' + $(this).val() + '</h2>';
		var template_id = $(this).data('template-id');
		var $question; 
		
		$('.js-questions-area').append($($('#' + template_id).html()).prepend(title));
		$question = $('.js-questions-area .question').last();
		
		save_question($question.find('.question-form'));
		bind_sortable_response_items();
	});
}

function bind_add_response_item(){
	$('.js-questions-area').on('click', '.js-add-reponse-item', function(){
		var template_id = $(this).data('template-id');
		var $question = $(this).parents('.question');
		$question
			.find('.js-response-items-area')
			.append($('#' + template_id).html());
		save_response_items($question);
	});
	
}

function bind_sortable(){
	$('.js-questions-area').sortable({
		// TODO DB 순서 변경 로직 추가
		handle: '.js-move-question, h2'
	});
}

function bind_sortable_response_items(){
	$('.js-questions-area .js-response-items-area').sortable({
		handle: '.js-move-response-item'
	});
}

function bind_remove(){
	$('.js-questions-area').on('click', '.js-remove-question', function(){
		var $question = $(this).parents('.question');
		delete_question($question);
	});
}

function bind_save(){
	$('.js-questions-area').on('blur', '.question-form input', function(){
		var $question_form = $(this).parents('.question-form');
		save_question($question_form);
	});
	
	$('.js-questions-area').on('blur', '.response-items-form input', function(){
		var $question = $(this).parents('.question');
		save_response_items($question);
	});
}

function save_question($question_form){
	var $question = $question_form.parents('.question');
	var question_obj = $question_form.serializeObject();
	
	// id가 있으면 수정. 이 경우 REST API에 맞게 HTTP method를 PUT으로 보낸다.
	if(question_obj.id){
		question_obj._method = 'PUT'; 
	}
	
	// 순서를 정한다.
	question_obj.orderNo = $question.index('.question');
	
	$.post(survey.context_path + '/questions', question_obj, function(insertedQuestion){
		$question_form.find('[name=id]').val(insertedQuestion.id);
		save_response_items($question);
		mynoty(insertedQuestion.content + ' 질문 관련 정보를 저장했습니다.');
	}, 'json');
}

function delete_question($question){
	var question_id = $question.find('.question-form [name=id]').val();
	
	// DB에 저장된 질문인 경우
	var params = {
		id: question_id,
		_method: 'DELETE'
	};
	
	$.post(survey.context_path + '/questions', params, function(deletedQuestion){
		var content = deletedQuestion.content || '';
		mynoty(content + ' 질문 관련 정보를 삭제했습니다.', {type: 'warning'});
		$question.remove();
	});
}

function save_response_items($question){
	var $question_form = $question.find('.question-form')
	var question_id = $question_form.find('[name=id]').val();
	
	$question.find('.js-response-item').each(function(index){
		var $response_item = $(this);
		var content = $(this).find('[name=content]').val();
		
		var param = {
			questionId: question_id,
			content: content,
			orderNo: index
		};
		
		var id = $(this).find('[name=id]').val();
		
		if(id){
			param.id = id;
			param._method = 'PUT';
		}
		
		$.post(survey.context_path + '/response_item', param, function(response_item){
			var response_item_content = '';
			$response_item.find('[name=id]').val(response_item.id);
			if(response_item.content){
				if(response_item.content == '$$$etc$$$'){
					response_item_content = '(기타 답항)';
				}else{
					response_item_content = '(' + response_item.content + ')';
				}
			}
			mynoty($question_form.find('[name=content]').val() + ' 질문의 답변' + response_item_content + '을 저장했습니다.');
		}, 'json');
	});
}