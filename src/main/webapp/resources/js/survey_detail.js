$(document).ready(function(){
	init_questions();
	bind_add_question();
	bind_add_response_item();
	bind_sortable();
	bind_sortable_response_items();
	bind_remove();
	bind_save();
});

function bind_add_question(){
	$('.js-add-question').click(function(){
		var type = $(this).data('template-id');
		var $question; 
		
		var question = {
			id: null,
			content: null,
			contentDetail: null,
			type: type
		}
		
		if(type == '점수범위'){
			question.responseItems = [{
				id: null,
				content: '{"min":"0","max":"10"}'
			}];
		}
		
		$question = add_question(question);
		
		save_question($question);
		bind_sortable_response_items();
	});
}

function add_question(question){
	var html = $('#' + question.type).html();
	var compiled = _.template(html);
	$('.js-questions-area').append(compiled(question));
	$qeustion = $('.question').last();
	
	if(question.responseItems){
		_.each(question.responseItems, function(response_item){
			add_response_item($qeustion, response_item);
		});
	}
	
	return $qeustion;
}

function bind_add_response_item(){
	$('.js-questions-area').on('click', '.js-add-reponse-item', function(){
		var type = $(this).data('template-id');
		var $question = $(this).parents('.question');
		var response_item = {
			id: null,
			content: null,
			type: type
		}
		
		add_response_item($question, response_item);
		save_response_items($question);
	});
	
}

function add_response_item($question, response_item){
	var question_type = $question.find('.question-form [name="type"]').val();
	var type, min_max;
	
	
	if( ! response_item.type){
		type = question_type + '-답항'; 
		if(response_item.content == '$$$etc$$$'){
			type = question_type + '-기타-답항';
		}
	}else{
		type = response_item.type;
	}
	
	var compiled = _.template($('#' + type).html());
	
	if(question_type == '점수범위'){
		min_max = $.evalJSON(response_item.content);
		response_item.min = min_max.min;
		response_item.max = min_max.max;
	}
	
	$question
		.find('.js-response-items-area')
		.append(compiled(response_item));
}

function bind_sortable(){
	var tmp = $('.js-questions-area').sortable({
		handle: '.js-move-question, h2',
	});
	tmp.on('sortstop', function(){
		$('.question').each(function(index, question){
			save_question($(question));
		});
	});
}

function bind_sortable_response_items(){
	var tmp = $('.js-questions-area .js-response-items-area').sortable({
		handle: '.js-move-response-item'
	});
	tmp.on('sortstop', function(e, ui){
		save_response_items(ui.item.parents('.question'));
	});
}

function bind_remove(){
	$('.js-questions-area').on('click', '.js-remove-question', function(){
		var $question = $(this).parents('.question');
		delete_question($question);
	});
	
	$('.js-questions-area').on('click', '.js-remove-response-item', function(){
		var $response_item = $(this).parents('.js-response-item');
		delete_response_item($response_item);
	});
}

function bind_save(){
	$('.js-questions-area').on('blur', '.question-form input, .question-form textarea', function(){
		var $question = $(this).parents('.question');
		save_question($question);
	});
	
	$('.js-questions-area').on('blur', '.js-response-items-area input', function(){
		var $question = $(this).parents('.question');
		save_response_items($question);
	});
}

function save_question($question){
	var $question_form = $question.find('.question-form');
	var question_obj = $question_form.serializeObject();
	
	// id가 있으면 수정. 이 경우 REST API에 맞게 HTTP method를 PUT으로 보낸다.
	if($.trim(question_obj.id)){
		question_obj._method = 'PUT'; 
	}
	
	// 순서를 정한다.
	question_obj.orderNo = $question.index('.question');
	
	$.post(survey.context_path + '/questions', question_obj, function(inserted_question){
		var inserted_question_content = inserted_question.content + ' 질문';
		if(inserted_question.content == '$$$pageBreaker$$$'){
			inserted_question_content = '페이지 나누기';
		}
		
		$question_form.find('[name=id]').val(inserted_question.id);
		
		mynoty(inserted_question_content + ' 관련 정보를 저장했습니다.');
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
		var message;
		if(deletedQuestion.content == '$$$pageBreaker$$$'){
			message = '페이지 나누기를 삭제했습니다.';
		}else if(deletedQuestion.content == null){
			message = '질문 관련 정보를 삭제했습니다.';
		}else{
			message = deletedQuestion.content + ' 질문 관련 정보를 삭제했습니다.';
		}
		mynoty(message, {type: 'warning'});
		$question.remove();
	});
}

function save_response_items($question){
	var $question_form = $question.find('.question-form')
	var question_id = $question_form.find('[name=id]').val();
	var type = $question.find('[name=type]').val();
	var point_range = {};
	var $response_item;
	
	if(type == '점수범위'){
		$response_item = $question.find('.js-response-item');
		var $min = $response_item.find('[name=min]');
		var $max = $response_item.find('[name=max]');
		point_range.min = $min.val();
		point_range.max = $max.val();
		
		// 두 값이 모두 입력될 때만 저장한다.
		if(point_range.max == '' || point_range.min ==''){
			return false;
		}
		
		// min, max 검증
		if(parseInt(point_range.max) < parseInt(point_range.min)){
			// 최솟값이 최댓값보다 큰 경우 두 값을 바꾼다.
			
			point_range = {
				min: point_range.max,
				max: point_range.min
			};
			
			$min.val(point_range.min);
			$max.val(point_range.max);
		}
		
		$response_item.find('[name=content]').val($.toJSON(point_range));
	}
	
	$question.find('.js-response-item').each(function(index){
		var $response_item = $(this);
		var content = $(this).find('[name=content]').val();
		
		var param = {
			questionId: question_id,
			content: content,
			orderNo: index
		};
		
		var id = $(this).find('[name=id]').val();
		
		if($.trim(id)){
			param.id = id;
			param._method = 'PUT';
		}
		
		$.post(survey.context_path + '/response_items', param, function(response_item){
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

function delete_response_item($response_item){

	var response_item_id = $response_item.find('[name=id]').val();
	
	var params = {
		id: response_item_id,
		_method: 'DELETE'
	};
	
	$.post(survey.context_path + '/response_items', params, function(deletedResponseItem){
		var content = deletedResponseItem.content || '';
		mynoty(content + ' 답변을 삭제했습니다.', {type: 'warning'});
		$response_item.remove();
	});
}

function init_questions(){
	_.each(survey.questions, function(question){
		add_question(question);
	});
}