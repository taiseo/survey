function print_bonbu_select_el(){
	$('#bonbu').append('<option value="">전체</option>');
	_.each(survey.branches, function(branch, index, list){
		var option = document.createElement("option");
		$('#bonbu').append('<option>' + index + '</option>');
	});
}

function bind_branch_select_el(){
	$('.js-search-by-branch').on('change', '#bonbu', print_branch_select_el);
}

function print_branch_select_el(){
	$('[name=branch]')
		.html('')
		.append('<option value="">전체</option>');
	var bonbu = $('#bonbu').val();
	if(bonbu == ''){
		_.each(survey.branches, function(el){
			_.each(el, function(branch){
				$('[name=branch]').append('<option>' + branch + '</option>');
			});
		});
	}else{
		_.each(survey.branches[bonbu], function(branch){
			$('[name=branch]').append('<option>' + branch + '</option>');
		});
	}
}