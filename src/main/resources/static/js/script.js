$(function(){
	$('.firstNav li').click(function(){
		var index=$(this).index();
		$(this).addClass('current').siblings().removeClass('current');		
		$('.sonNav').eq(index).show().siblings('.sonNav').hide();
		//$('.sonNav').eq(index).show();
	})

	$('.bottomBtn input,.imgP').click(function(){
		$('.masker').hide();
	})

	$('.add,.changeInfo').click(function(){
		$('.addPop').show();
	})
	$('.deleteInfo').click(function(){
		$('.delPop').show();
	})

	$('.changeInfo,.lookInfo').click(function(){
		$('.changeSPop').show();
	})
})