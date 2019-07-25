/*!
 * blogedit.html 页面脚本.
 * 
 * @since: 1.0.0 2017-03-26
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=blogedit.js

// DOM 加载完再执行
$(function() {
	
	// 初始化 md 编辑器
    $("#md").markdown({
        language: 'zh',
        fullscreen: {
            enable: true
        },
        resize:'vertical',
        localStorage:'md',
        imgurl: '/upload',
        base64url: '/upload'
    });
  
    // 初始化标签控件
    $('.form-control-tag').tagEditor({
        initialTags: [],
        maxTags: 5,
        delimiter: ', ',
        forceLowercase: false,
        animateDelete: 0,
        placeholder: '请输入标签'
    });
    
    $('.form-control-chosen').chosen();
 
 	$("#uploadImage").click(function() {
		$.ajax({
		    url: '/upload',
		    type: 'POST',
		    cache: false,
		    data: new FormData($('#uploadformid')[0]),
		    processData: false,
		    contentType: false,
		    success: function(data){
		    	var mdcontent=$("#md").val();
		    	 $("#md").val(mdcontent + "\n![]("+data +") \n");
 
	         }
		}).done(function(res) {
			$('#file').val('');
		}).fail(function(res) {});
 	})

 	// 发布博客
 	$("#submitBlog").click(function() {

		// 获取 CSRF Token 
		// var csrfToken = $("meta[name='_csrf']").attr("content");
		// var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var username = $(this).attr("username");
		var id = null;
		if (Number($('#id').val()) != 0){
			id = Number($('#id').val());
		}else{
			id = null;
		}
		$.ajax({
		    url: '/u/'+ $(this).attr("userName") + '/blogs/edit',
		    type: 'POST',
			contentType: "application/json; charset=utf-8",
		    data:JSON.stringify({
				"id":id,
		    	"title": $('#title').val(), 
		    	"summary": $('#summary').val() , 
		    	"content": $('#md').val(),
				"htmlContent":$('#md').val(),
				"viewed":Number($('#reading').val()),
				"comment":Number($('#comments').val()),
				"likes":Number($('#likes').val()),
				"catalog":{"id":$('#catalogSelect').val()},
				"tags":$('.form-control-tag').val()
		    }),
			// beforeSend: function(request) {
			//     request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
			// },
			 success: function(data){
				 if (data.success) {
					// 成功后，重定向
					 window.location = data.body;
					//  console.log(1);
				 } else {
					 toastr.error("error!"+data.message);
				 }
				 
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		})
 	})

	$('.form-control-tag').tagsInput({
		'defaultText':'输入标签'
	});
 	
});