/*!
 * u main JS.
 *
 * @since: 1.0.0 2017/3/9
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=u.js

// DOM 加载完再执行
$(function() {
	var _pageSize; // 存储用于搜索


	// 关键字搜索
	$("#searchBlogs").click(function() {
		$.ajax({
			url:'/u/' + username + '/blogs/tag',
			data:{
				"keyword":$("#keyword").val()
			},
			success:function (data) {
				// console.log(data);
				$("#mainContainer").html(data);
			}
		});
		// getBlogsByName(0, _pageSize);
	});

	// 最新\最热切换事件
	$(".nav-item .nav-link").click(function() {

		var url = $(this).attr("url");

		// 先移除其他的点击样式，再添加当前的点击样式
		$(".nav-item .nav-link").removeClass("active");
		$(this).addClass("active");

		// 加载其他模块的页面到右侧工作区
		 $.ajax({
			 url: url,
			 success: function(data){
				 $("#mainContainer").html(data);
			 },
			 error : function() {
				 toastr.error("error!");
			 }
		 })

		 // 清空搜索框内容
		 $("#keyword").val('');
	});

	// 获取分类列表
	function getCatalogs(username) {
		// 获取 CSRF Token

		$.ajax({
			url: '/catalogs',
			type: 'GET',
			data:{"username":username},
			success: function(data){
				$("#catalogMain").html(data);
			},
			error : function() {
				toastr.error("error!");
			}
		});
	}


	// 获取编辑分类的页面
	$(".blog-content-container").on("click",".blog-add-catalog", function () {
		$.ajax({
			url: '/catalogs/edit',
			type: 'GET',
			success: function(data){
				$("#catalogFormContainer").html(data);
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	// 获取编辑某个分类的页面
	$(".blog-content-container").on("click",".blog-edit-catalog", function () {

		$.ajax({
			url: '/catalogs/edit/'+$(this).attr('catalogId'),
			type: 'GET',
			success: function(data){
				$("#catalogFormContainer").html(data);
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	// 提交分类
	$("#submitEditCatalog").click(function() {
		// 获取 CSRF Token
		// var csrfToken = $("meta[name='_csrf']").attr("content");
		// var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			url: '/catalogs',
			type: 'POST',
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify({"username":username, "catalog":{"id":$('#catalogId').val(), "name":$('#catalogName').val()}}),
			// beforeSend: function(request) {
			// 	request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
			// },
			success: function(data){
				if (data.success) {
					toastr.info(data.message);
					// 成功后，刷新列表
					getCatalogs(username);
				} else {
					toastr.error(data.message);
				}
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	// 删除分类
	$(".blog-content-container").on("click",".blog-delete-catalog", function () {
		// 获取 CSRF Token
		// var csrfToken = $("meta[name='_csrf']").attr("content");
		// var csrfHeader = $("meta[name='_csrf_header']").attr("content");

		$.ajax({
			// url: '/catalogs/'+$(this).attr('catalogid')+'?username='+username,
			url: '/catalogs/'+$(this).attr('catalogid'),
			type: 'DELETE',
			// beforeSend: function(request) {
			// 	request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
			// },
			success: function(data){
				if (data.success) {
					toastr.info(data.message);
					// 成功后，刷新列表
					getCatalogs(username);
				} else {
					toastr.error(data.message);
				}
			},
			error : function() {
				toastr.error("error!");
			}
		});
	});

	$('#getCatalogView').click(function () {
		$.ajax({
			url:'/u/' + username + '/blogs/catalog',
			type: 'GET',
			success:function (data) {
				$('#mainContainer').html(data);
				document.body.scrollTop = 0;
				document.documentElement.scrollTop = 0;
			},
			error:function () {
				toastr.error("error!");
			}

		});
	});


	var username = $('.blog-edit-avatar').attr('username');
	var catalogId;


	getCatalogs(username);

});