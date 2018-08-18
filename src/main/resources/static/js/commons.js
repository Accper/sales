$(document).ready(function () {
    $.fn.datepicker.defaults.language = "zh-CN";
    $.fn.datepicker.defaults.autoclose = true;


});

/**
 * 日期格式化 返回 yyyy--MM-dd HH:mm:ss
 * @param value
 * @returns {*}
 */
var dateTimeFormatter = function (value) {
    if (value == undefined || value == '') {
        return '-';
    }
    var d = new Date(value),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hours = d.getHours(),
        mintues = '' + d.getMinutes(),
        seconds = '' + d.getSeconds();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (mintues.length < 2) mintues = '0' + mintues;
    if (seconds.length < 2) seconds = '0' + seconds;

    return ([year, month, day].join('-')).concat(['&nbsp']).concat([hours, mintues, seconds].join(':'));
};

/**
 * 日期格式化 返回 yyyy--MM-dd HH:mm:ss
 * @param value
 * @returns {*}
 */
var dateTimeFormatterCN = function (value) {
    if (value == undefined || value == '') {
        return '';
    }
    var d = new Date(value),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hours = d.getHours(),
        mintues = '' + d.getMinutes(),
        seconds = '' + d.getSeconds();


    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (mintues.length < 2) mintues = '0' + mintues;
    if (seconds.length < 2) seconds = '0' + seconds;

    return ([year, month, day].join('-')).concat([' ']).concat([hours, mintues].join(':'))
}

var dateTimeFormatterHM = function (value) {
    if (value == undefined || value == '') {
        return '';
    }
    var d = new Date(value),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear(),
        hours = d.getHours(),
        mintues = '' + d.getMinutes(),
        seconds = '' + d.getSeconds();


    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    if (mintues.length < 2) mintues = '0' + mintues;
    if (seconds.length < 2) seconds = '0' + seconds;

    return [hours, mintues].join(':');
}

/**
 * 日期格式化
 * @param value    json处理后的日期,eg:1484755200000
 * @returns yyyy-MM-dd
 * @author zhoujl
 */
var dateFormatter = function (value) {
    if (value == undefined || value == '') {
        return '';
    }
    var d = new Date(value),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
};
/**

 * 日期格式化
 * @param value    json处理后的日期
 * @returns yyyy
 * @author sgwang
 */
var dateFormatterToYear = function (value) {
    if (value == undefined) {
        return "";
    }
    var d = new Date(value),
        year = d.getFullYear();
    return year;
};
/**
 * 金额格式化 12345.678 -> 12,345.68
 * @param value
 * @author zhoujl
 */
var moneyFormatter = function (value) {
    if (!$.isNumeric(value)) {
        return;
    }
    if (value == undefined || value == '') {
        value = 0;
    }

    return Number(value).formatMoney(2);
};
/**
 * 百分号格式化 0.04 -> 4.00%
 * @param value
 * @returns {string}
 * @author zhoujl
 */
var percentageFormatter = function (value) {
    if (!$.isNumeric(value)) {
        return '';
    }

    return (value * 100).toFixed(2) + "%";
};


Number.prototype.formatMoney = function (c, d, t) {
    var n = this,
        c = isNaN(c = Math.abs(c)) ? 2 : c,
        d = d == undefined ? "." : d,
        t = t == undefined ? "," : t,
        s = n < 0 ? "-" : "",
        i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))),
        j = (j = i.length) > 3 ? j % 3 : 0;
    return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
};


/**
 * table 初始化查询条件
 *
 * @author zhoujl
 */
var custQueryParams = function (params, custParam) {
    var pageNum = (params.offset / params.limit) + 1;
    return $.extend(custParam, {
        pageSize: params.limit,   //页面大小
        pageNum: pageNum //页码
    })
};

/**
 * 重新加载table, 用于点击查询后重新加载表格
 * @param tableId       表格id
 * @param jsonDataParam 查询条件, 需要组成json格式. eg: {id:1, name:name}
 *
 * @author zhoujl
 */
var reloadTable = function (tableId, jsonDataParam) {
    $("#" + tableId).bootstrapTable('refreshOptions', {
        pageNumber: 1,
        queryParams: function (params) {
            return custQueryParams(params, jsonDataParam);
        }
    });
};


var responseBeanHandler = function (res) {

    var result = {};
    if (res.page != null) {
        result.total = res.page.total;
        console.log("result.total: " + result.total);
    } else {
        result.total = 0;
        console.log("result.total: " + result.total);
    }

    if (res.data != null) {
        result.rows = res.data;
    }
    else {
        result.rows = [];
    }
    return result;
};

/**
 * 判断一个数组中是否有重复值的方法
 *
 * @author zhoujl
 */
var checkArrayRepeat = function (array) {
    var hash = {};
    for (var i in array) {
        if (hash[array[i]]) {
            return true;
        }
        hash[array[i]] = true;
    }
    return false;
};

/**
 * 显示错误信息
 *
 * @author zhoujl
 */
var showToast = function (message) {
    $('.toast').text(message).show().delay(3000).fadeOut();
};