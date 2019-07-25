/*!
 * Avatar JS.
 * 
 * @since: 1.0.0 2017/4/6
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=main.js
 
// DOM 加载完再执行
$(function() {
	var avatarApi;
	
	// 获取编辑用户头像的界面
	$(".blog-content-container").on("click",".blog-edit-avatar", function () { 
		avatarApi = "/u/"+$(this).attr("userName")+"/avatar";
		$.ajax({ 
			 url: avatarApi, 
			 success: function(data){
			 	// if (data.success) {
			 	// 	$("#avatarFormContainer").html(data);
				// }else {
			 	// 	toastr.error(data.message);
				// }
				 $("#avatarFormContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	/**  
	 * 将以base64的图片url数据转换为Blob  
	 * @param urlData  
	 *            用url方式表示的base64图片数据  
	 */  
	function convertBase64UrlToBlob(urlData){  
	      
	    var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  
	      
	    //处理异常,将ascii码小于0的转换为大于0  
	    var ab = new ArrayBuffer(bytes.length);  
	    var ia = new Uint8Array(ab);  
	    for (var i = 0; i < bytes.length; i++) {  
	        ia[i] = bytes.charCodeAt(i);  
	    }  
	  
	    return new Blob( [ab] , {type : 'image/png'});  
	} 
	
	// 提交用户头像的图片数据
	$("#submitEditAvatar").on("click", function () { 
		var form = $('#avatarformid')[0];  
	    var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数
		console.log(JSON.stringify(formData));
	    var base64Codes = $(".cropImg > img").attr("src");
 	    formData.append("file",convertBase64UrlToBlob(base64Codes));  //append函数的第一个参数是后台获取数据的参数名,和html标签的input的name属性功能相同
	    // console.log(form);
		// console.log(JSON.stringify(formData));
		// console.log(formData.get("file"));
	    // console.log(base64Codes);
 	    $.ajax({
		    url: '/upload',
		    type: 'POST',
		    cache: false,
		    data: formData,
		    processData: false,
		    contentType: false,
		    success: function(data){
		    	if (data.message){
		    		var avatarUrl = data;
					// 获取 CSRF Token
					// var csrfToken = $("meta[name='_csrf']").attr("content");
					// var csrfHeader = $("meta[name='_csrf_header']").attr("content");
					// 保存头像更改到数据库
					console.log(data);
					avatarUrl = "http://" + avatarUrl.body;
					$.ajax({
						url: avatarApi,
						type: 'POST',
						contentType: "application/json; charset=utf-8",
						data: JSON.stringify({"id":Number($("#userId").val()), "avatar":avatarUrl}),
						// beforeSend: function(request) {
						//     request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
						// },
						success: function(data){
							if (data.success) {
								// 成功后，置换头像图片
								$(".blog-avatar").attr("src", data.avatarUrl);
							} else {
								toastr.error("error!"+data.message);
							}
							window.location.reload();
						},
						error : function() {
							toastr.error("error!");
						}
					});
				} else {
		    		toastr.error("上传失败，请重试!");
				}
	        },
		    error : function() {
		    	 toastr.error("error!");
		    }
		})
	});
 

	 
});