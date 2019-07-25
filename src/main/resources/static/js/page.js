$(function () {

    pageSearch = function (pageNum) {
        var url = document.location.toString();
        var arrUrl = url.split("//");

        var start = arrUrl[1].indexOf("/");
        var relUrl = arrUrl[1].substring(start);//stop省略，截取从start开始到结尾的所有字符

        if(relUrl.indexOf("?") != -1){
            relUrl = relUrl.split("?")[0];
        }
        $.ajax({
            url:relUrl + '?pageNum=' + pageNum,
            type:'GET',
            success:function (data) {
                $('#mainContainer').html(data);
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            },
            error:function () {
                toastr.error("error!");
            }
        })
    };

    catalogSearch = function (username,catalogId) {
        $.ajax({
            url:'/u/' + username + '/blogs/catalog/' + catalogId + '?pageNum=0',
            type:'GET',
            success:function (data) {
                $('#mainContainer').html(data);
                var stateObject = {};
                var newUrl = '/u/' + username + '/blogs/catalog/' + catalogId;
                history.pushState(stateObject,'',newUrl);
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            },
            error:function () {
                toastr.error("error!");
            }
        });
    };

});