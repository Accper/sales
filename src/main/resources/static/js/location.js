$(function () {
    $('.checkTime input').datepicker({});
    console.log("location loaded");
    $("#detail_tb").bootstrapTable({
        url: "class/search",
        method: 'get',
        pagination: true, //分页
        singleSelect: false,
        striped: true, //是否显示行间隔色
        cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: "server", //可选值为 'client' 或者 'server'
        clickToSelect: true,

        queryParams: function (params) {
            return custQueryParams(params);
        },
        responseHandler: function (res) {
            return responseBeanHandler(res);
        },
        columns: [
            {checkbox: true},
            {field: 'locationCode', align: 'center', title: '考场名称'},
            {
                title: '考试时间', formatter: function (value, row) {
                var date = '';
                var start = dateTimeFormatter(row.startTime);
                var end = dateTimeFormatter(row.endTime);
                var first = start.split("&nbsp");
                var second = first[0].split("-")
                var third = first[1].split(":")
                date = date + second[0] + "-" + second[1] + "-" + second[2] + "  " + third[0] + ":" + third[1] + " 至";
                first = end.split("&nbsp");
                third = first[1].split(":");
                date = date + third[0] + ":" + third[1] + "";
                return date;
            }
            },
            {
                title: 'imei号', formatter: function (value, row) {
                var text = '';
                row.devices && row.devices.forEach(function (item) {
                    text += item.imei + '，';
                });
                text = text.substring(0, text.length - 1);
                return text;
            }
            },
            {
                title: '操作', formatter: function (value, row) {
                var text = '';
                if ((row.startTime - new Date().getTime()) > 1800000) {
                    text = text + '<a href="javascript:void(0);" data-id="' + row.id + '" class="modify_location">修改</a>';
                    text = text + '<a href="javascript:void(0);" data-id="' + row.id + '" class="delete_location">删除</a>';

                } else {
                    text = '<a href="javascript:void(0);" data-id="' + row.id + '" class="view_location">查看</a>';

                }
                return text;

            }
            }
        ]
    });

    //根据关键字查询
    $("#search").click(function () {
        refreshTable();
    });

    //回车即可根据keyWord查询
    $('#keyWord').bind('keypress', function (event) {//监听回车事件
        if (event.keyCode == "13") {
            refreshTable();
        }
    });

    //导出数据
    $("#export").click(function () {
        var selecteds = $("#detail_tb").bootstrapTable('getSelections');
        console.log(selecteds);

        var ids = '';
        $.each(selecteds, function (index, row) {
            console.log(row);
            ids += row.id + ',';
        });

        if (ids.length != 0) {

            window.open('class/export/excel?param=' + ids + '');

        } else {
            showToastAndDisableCommitBtn("请勾选需要导出的数据");
        }

    });


    //打开修改页面
    $("#detail_tb").on('click', '.modify_location', function (e) {
        e.preventDefault();
        $("#confirm_submit").attr("disabled", false);
        var id = $(this).data('id');
        $('#locationInfoModel').show();
        $('#device0').val('');
        $('#device1').val('');
        $('#device2').val('');
        $('#device3').val('');
        //更新url
        url = 'class/update/' + id;
        $('#model-label').text('修改考场');
        //查询考场信息并显示到页面
        $.ajax({
            url: 'class/select/' + id,
            method: 'get',
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;

                $("#locationId").val(data.id);
                $("#locationCode").val(data.locationCode);
                $("#startTime").val(formatDateTime(data.startTime));
                $("#endTime").val(formatDateTime(data.endTime));
                //需要修改的devices信息
                for (var i = 0; i < data.devices.length; i++) {

                    $("#deviceId" + i).val(data.devices[i].id);
                    $("#device" + i).val(data.devices[i].imei);
                }

            }
        })
    });


    //打开删除页面
    $("#detail_tb").on('click', '.delete_location', function (e) {
        e.preventDefault();

        var id = $(this).data('id');
        $('#locationInfoModel2').show();
        $('#model-label2').text('删除考场');
        //查询考场信息并显示到页面
        $.ajax({
            url: 'class/select/' + id,
            method: 'get',
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;
                $("#locationCode2").val(data.locationCode);
                $("#locationId2").val(id);
            }
        })
    });

    //逻辑删除
    //保存
    $("#confirm_submit2").click(function () {
        console.log($('#locationCode2').val());
        console.log($('#locationId2').val());
        //调用删除
        $.ajax({
            url: 'class/delete/' + $('#locationId2').val(),
            method: 'get',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',

            success: function (response) {
                if (response != undefined && response.status == 1) {
                    //保存成功后关闭modal, 刷新Table
                    $('#locationInfoModel2').hide();
                    refreshTable();
                } else {
                    // 显示错误信息
                }
            }
        })
    });





    //打开查看页面
    $("#detail_tb").on('click', '.view_location', function (e) {
        e.preventDefault();
        var id = $(this).data('id');
        $('#locationInfoModel').show();
        $('#device0').val('');
        $('#device1').val('');
        $('#device2').val('');
        $('#device3').val('');
        //更新url
        url = 'class/update/' + id;
        $('#model-label').text('查看考场');
        //查询考场信息并显示到页面
        $.ajax({
            url: 'class/select/' + id,
            method: 'get',
            dataType: 'json',
            success: function (response) {
                if (response.status != 1) {
                    return;
                }
                var data = response.data;

                $("#locationId").val(data.id);
                $("#locationCode").val(data.locationCode);
                $("#startTime").val(formatDateTime(data.startTime));
                $("#endTime").val(formatDateTime(data.endTime));
                //需要修改的devices信息
                for (var i = 0; i < data.devices.length; i++) {

                    $("#deviceId" + i).val(data.devices[i].id);
                    $("#device" + i).val(data.devices[i].imei);
                }

            }
        })
        $("#confirm_submit").attr("disabled", true);

    });





    //打开新增页面
    $("#insert").on('click', function (e) {
        e.preventDefault();

        //新增url
        url = 'class/create/';
        $('#model-label').text('新增考场');
        $('#locationInfoModel li input, #locationInfoModel input[type=hidden]').val('');

        $('#locationInfoModel').show();
    });

    //保存
    $("#confirm_submit").click(function () {
        //  console.log($("#startTime").val());
        var location = {
            locationCode: $("#locationCode").val(),
            startTime: new Date($("#startTime").val().split(" ")[0].split("-")[0], $("#startTime").val().split(" ")[0].split("-")[1], $("#startTime").val().split(" ")[0].split("-")[2], $("#startTime").val().split(" ")[1].split(":")[0], $("#startTime").val().split(" ")[1].split(":")[1], $("#startTime").val().split(" ")[1].split(":")[2]),
            endTime: new Date($("#endTime").val().split(" ")[0].split("-")[0], $("#endTime").val().split(" ")[0].split("-")[1], $("#endTime").val().split(" ")[0].split("-")[2], $("#endTime").val().split(" ")[1].split(":")[0], $("#endTime").val().split(" ")[1].split(":")[1], $("#endTime").val().split(" ")[1].split(":")[2]),
            devices: [
                {id: $("#deviceId0").val(), imei: $("#device0").val()},
                {id: $("#deviceId1").val(), imei: $("#device1").val()},
                {id: $("#deviceId2").val(), imei: $("#device2").val()},
                {id: $("#deviceId3").val(), imei: $("#device3").val()}]
        };
        //调用保存方法
        $.ajax({
            url: url,
            method: 'post',
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(location),
            success: function (response) {
                if (response != undefined && response.status == 1) {
                    //保存成功后关闭modal, 刷新Table
                    $('#locationInfoModel').hide();
                    refreshTable();
                } else {
                    // 显示错误信息
                }
            }
        })
    });

    /**
     * 关闭model
     *
     * @author zhoujl
     */
    $('#cancel, #locationInfoModel .imgP').click(function () {
        $('#locationInfoModel').hide();
    });

    /**
     * 关闭删除的model
     * @author taoye
     */
    $('#cancel2, #locationInfoModel2 .imgP').click(function () {
        $('#locationInfoModel2').hide();
    });


    /**
     * 验证 imei
     *
     * @author zhoujl
     */
    $('#locationInfoModel .device-imei').blur(function (e) {
        e.preventDefault();
        var imeiCode = $(this).val();
        var allImeiCodes = [];
        $.each($('#locationInfoModel .device-imei'), function (index, comp) {
            if (comp != undefined && $(comp).val() != '') {
                allImeiCodes.push($(comp).val());
            }
        });

        var isDuplicate = checkArrayRepeat(allImeiCodes);
        if (isDuplicate) {
            showToastAndDisableCommitBtn('同一考场的不同设备不能使用相同设备号:' + imeiCode);
        }

        if ($(this).val().length != 0 ) {
            $.ajax({
                url: 'class/examer/' + $(this).val(),
                method: 'get',
                dataType: 'json',
                data: {locationId: $('#locationId').val()},
                contentType: 'application/json;charset=utf-8',
                success: function (response) {
                    if (response == undefined || response.status != 1) {
                        //后台错误
                        return;
                    }

                    if (!response.data) {
                        showToastAndDisableCommitBtn('设备号:' + imeiCode + '已经存在!');
                    }
                }
            });
        }


    });

    /**
     * location code 校验
     */
    $('#locationCode').blur(function () {
        var locationCode = $(this).val();
        if (locationCode.length == 0) {
            showToastAndDisableCommitBtn('考场名称不能为空!');
        } else {
            $.ajax({
                url: 'class/checker/' + $('#locationCode').val(),
                method: 'get',
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: {locationId: $('#locationId').val()},
                success: function (response) {
                    if (response == undefined || response.status != 1) {
                        return;
                    }

                    if (!response.data) {
                        showToastAndDisableCommitBtn('考场名称:' + locationCode + '已经存在!');
                    }
                }

            })
        }
    });

    /**
     * 验证 time
     *
     * @author taoye
     */
    $('#locationInfoModel .location-time').blur(function (e) {
        e.preventDefault();
        var times = [];
        $.each($('#locationInfoModel .location-time'), function (index, comp) {
            if (comp != undefined && $(comp).val() != '') {
                times.push($(comp).val());
            }
        });

        if (times.length != 2) {
            showToastAndDisableCommitBtn("考试时间不能为空");
        } else if (formatStringToDate(times[0]) > formatStringToDate(times[1])) {
            showToastAndDisableCommitBtn("开始时间不能超过结束时间");
        }
    });


});

//刷新Table
var refreshTable = function () {
    alert("demo");
    var param = {
        keyWord: $("#keyWord").val()

    };
    reloadTable("detail_tb", param);
};

var showToastAndDisableCommitBtn = function (message) {
    showToast(message);
    //button 不可用.
    // $('#confirm_submit').addClass('commit-btn-disable');
};

//时间戳转换成，例如2017-11-20 09:30:21
var formatDateTime = function (inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
};

var formatStringToDate = function (string) {
    return new Date(string.split(" ")[0].split("-")[0], string.split(" ")[0].split("-")[1], string.split(" ")[0].split("-")[2], string.split(" ")[1].split(":")[0], string.split(" ")[1].split(":")[1], string.split(" ")[1].split(":")[2]);
};



