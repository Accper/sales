
var _ctx = $("meta[name='ctx']").attr("content");
$(document).ready(function () {

    /**
     * load first level menu.
     *
     * @author zhoujl
     */
    $.ajax({
        url: _ctx + "/menu/first",
        method: 'get',
        dataType: 'json',
        success: function (rsp) {
            console.log("load first meau");
            if (rsp.status !== 1) {
                return;
            }

            //load menu
            var html = '';
            $.each(rsp.data, function (index, row) {
                html += '<li><a href="javascript:void(0);" style="text-decoration:none;" data-id="' + row.id + '"><span><img src="' + row.imageUrl + '" /></span>' + row.menuName + '</a></li>'
            });

            //set active class and load second level menu
            $("#firstNav").html(html).find("li:first").addClass("current").click();

        }
    });

    /**
     * 点一级菜单, 加载二级菜单
     *
     * @author zhoujl
     */
    $('#firstNav').on('click', 'li', function (e) {
        e.preventDefault();

        $(this).parent("ul").children(".current").removeClass("current");
        $(this).addClass("current");

        var $a = $(this).find("a");
        var menuName = $.trim($a.text());

        $.ajax({
            url: _ctx +'/menu/second/' + $a.data('id'),
            method: 'get',
            dataType: 'json',
            success: function (rsp) {
                console.log("click second meau");
                if (rsp.status != 1) {
                    return
                }

                var html = '<h2 class="addNewStyle" >' + menuName + '</h2>' + '<ul>';
                $.each(rsp.data, function (index, row) {
                    html += '<li><a style="text-decoration:none;" href="' + _ctx + row.url + '">' + row.menuName + '</a></li>';
                });
                html += '</ul>';

                $('#mainFrame .sonNav').html(html).find("li:first").addClass("current").click();
            }
        })
    });

    /**
     * 点二级菜单, 加载主页面
     *
     * @author zhoujl
     */
    $('#sonNav').on('click', 'li', function (e) {
        e.preventDefault();
        $(this).parent('ul').children('.current').removeClass('current');
        $(this).addClass('current');

        $.ajax({
            url: $(this).find('a').prop('href'),
            method: 'get',
            async: false,
            dataType: 'html',
            success: function (html) {
                //返回的html如果包含'登录' 这两个字, 返回登录页面
                if (html.indexOf('登录') > -1) {
                    console.log("ajaxError login")
                    window.location.href = 'login';
                }
                $("#main_content").html(html);
            }
        })
    });
});

$(document).ajaxError(function (e, xhr, options) {
    console.log("ajaxError login")
    //如果返回的结果是html返回login页面.
    if (xhr.status == 403 || xhr.responseText.indexOf('<html>') > -1) {
        window.location.href = 'login';
    }
});

