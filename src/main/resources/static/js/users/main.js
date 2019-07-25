/*!
  * Bolg main JS.
 * 
 * @since: 1.0.0 2017/3/9
 * @author Way Lau <https://waylau.com>
 */
"use strict";
//# sourceURL=main.js

// DOM 加载完再执行
$(function () {

    var _pageSize; // 存储用于搜索

    // 根据用户名、页面索引、页面大小获取用户列表
    function getUersByName(pageIndex, pageSize) {
        $.ajax({
            url: "/user",
            contentType: 'application/json',
            data: {
                "async": true,
                "pageIndex": pageIndex,
                "pageSize": pageSize,
                "name": $("#searchName").val()
            },
            success: function (data) {
                $("#mainContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }

    // 获取添加用户的界面
    $("#addUser").click(function () {
        $.ajax({
            url: "/user/add",
            success: function (data) {
                $("#userFormContainer").html(data);
            },
            error: function (data) {
                toastr.error("error!");
            }
        });
    });

    $(".blog-edit-user").click(function () {
        $.ajax({
            url: "/user/modify/" + $(this).attr("userId"),
            success: function (data) {
                $("#userFormContainer").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    // 提交变更后，清空表单
    $("#submitEdit").click(function () {
        $.ajax({
            url: "/user",
            type: 'POST',
            data: $("#userForm").serialize(),
            // data: {"username": 123, "password": 123456},
            success: function (data) {
                window.location.reload();

            },
            error: function () {
                toastr.error("error!");
            }
        });
    });

    $(".blog-delete-user").click(function () {
        $.ajax({
            url: "/user/delete/" + $(this).attr("userId"),
            success: function () {
                window.location.reload();
            }
        });
    });
});