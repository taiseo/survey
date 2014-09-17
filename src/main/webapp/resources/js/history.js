$(document).ready(function(){
	bind_name_by_part();
	print_bonbu_select_el();
	print_branch_select_el();
	bind_branch_select_el();
});

survey.branches = {
	'경기' : ['여주이천지사', '양평광주서울지사', '화성수원지사', '연천포천지사', '파주지사', '고양지사', '강화지사', '김포지사', '평택지사', '안성지사'],
	'강원' : ['홍천춘천지사', '원주지사', '강릉지사', '영북지사', '철원지사'],
	'충북' : ['청주지사', '보은지사', '옥천영동지사', '진천지사', '괴산증평지사', '음성지사', '충주제천단양지사'],
	'충남' : ['천안지사', '공주지사', '보령지사', '아산지사', '서산태안지사', '논산지사', '세종대전금산지사', '부여지사', '서천지사', '청양지사', '홍성지사', '예산지사', '당진지사'],
	'전북' : ['남원지사', '순창지사', '동진지사', '부안지사', '군산지사', '익산지사', '전주완주임실지사', '고창지사', '정읍지사', '무진장지사'],
	'전남' : ['광주지사', '순천광양여수지사', '나주지사', '담양지사', '곡성지사', '구례지사', '고흥지사', '보성지사', '화순지사', '장흥지사', '강진완도지사', '해남지사', '영암지사', '무안신안지사', '함평지사', '영광지사', '장성지사', '진도지사'],
	'경북' : ['포항을릉지사', '경주지사', '안동지사', '구미김천지사', '영주봉화지사', '영천지사', '상주지사', '문경지사', '경산청도지사', '의성군위지사', '청송영양지사', '영덕울진지사', '고령지사', '성주지사', '칠곡지사', '예천지사', '달성지사'], 
	'경남' : ['김해양산지사', '고성통영거제지사', '울산지사', '진주산청지사', '의령지사', '함안지사', '창녕지사', '밀양지사', '창원지사', '사천지사', '거창함양지사', '합천지사', '하동남해지사'],
	'제주' : ['지사 없음']
};

function bind_name_by_part(){
	$('[name=part]').change(function(){
		if($(this).val() == ''){
			$('[name=username] option').show();
		}else{
			$('[name=username]').val("");
			$('[name=username] option').hide();
			$('[name=username] option.js-part-' + $(this).val()).show();
			$('[name=username] option.js-all').show();
			
		}
	});
}

function search_by_branch(){
	var params = {
		startDate: $('[name=startDate]').val(),
		endDate: $('[name=endDate]').val(),
		branch: $('[name=branch]').val()
	}
	
	$.post(survey.context_path + '/history/search-by-branch', params, function(html){
		$('.js-search-result').html(html);
	});
}

function search_by_user(){
	var params = {
		startDate: $('[name=startDate]').val(),
		endDate: $('[name=endDate]').val(),
		part: $('[name=part]').val(),
		username: $('[name=username]').val()
	}
	
	$.post(survey.context_path + '/history/search-by-user', params, function(html){
		$('.js-search-result').html(html);
	});
}

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