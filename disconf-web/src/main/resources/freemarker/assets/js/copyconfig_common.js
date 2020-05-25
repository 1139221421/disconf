//
// 获取APP信息
//
$.ajax({
    type: "GET",
    url: "/api/app/list"
}).done(
    function (data) {
        if (data.success === "true") {
            var clean = true;
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                if (appId == item.id) {
                    $("#appChoiceA span:first-child").text("APP:" + item.name);
                    clean = false;
                }
                html += '<li><a rel=' + item.id + ' href="#">APP: '
                    + item.name + '</a></li>';
            });
            $("#appChoice").html(html);
            $("#appChoice1").html(html);
            if (clean) {
                appId = -1;
            }
            if (appId != -1) {
                fetchVersion(appId);
            }
        }
    });

$("#appChoice").on('click', 'li a', function () {
    $("#appChoiceA span:first-child").text($(this).text());
    appId = $(this).attr('rel');
    fetchVersion(appId);
});

$("#appChoice1").on('click', 'li a', function () {
    $("#appChoiceA1 span:first-child").text($(this).text());
    copyAppId = $(this).attr('rel');
    fetchVersion1(copyAppId);
});

//
// 获取版本信息
//
function fetchVersion(appId) {

    $("#versionChoiceA span:first-child").text("选择版本");

    $.ajax({
        type: "GET",
        url: "/api/web/config/versionlist?appId=" + appId
    }).done(function (data) {
        if (data.success === "true") {
            var clean = true;
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                if (version == item) {
                    $("#versionChoiceA span:first-child").text(version);
                    clean = false;
                }
                html += '<li><a href="#">' + item + '</a></li>';
            });
            $("#versionChoice").html(html);
            if (clean) {
                version = "";
            }
        }
    });

    $("#versionChoice").on('click', 'li a', function () {
        $("#versionChoiceA span:first-child").text($(this).text());
        version = $(this).text();
    });
}

function fetchVersion1(appId) {
    console.log(1)

    $("#versionChoiceA1 span:first-child").text("选择版本");
    copyVersion = "";
    $.ajax({
        type: "GET",
        url: "/api/web/config/versionlist?appId=" + appId
    }).done(function (data) {
        if (data.success === "true") {
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                html += '<li><a href="#">' + item + '</a></li>';
            });
            html += '<li><a href="#">' + "自定义版本" + '</a></li>';
            $("#versionChoice1").html(html);
        }
    });

    $("#versionChoice1").on('click', 'li a', function () {
        $("#versionChoiceA1 span:first-child").text($(this).text());
        copyVersion = $(this).text();
        if (copyVersion == '自定义版本') {
            $("#selfversion").show();
        } else {
            $("#selfversion").hide();
        }
    });
}

//
// 获取Env信息
//
$.ajax({
    type: "GET",
    url: "/api/env/list"
}).done(
    function (data) {
        if (data.success === "true") {
            var clean = true;
            var html = "";
            var result = data.page.result;
            $.each(result, function (index, item) {
                if (envId == item.id) {
                    $("#envChoiceA span:first-child").text("环境:" + item.name);
                    clean = false;
                }
                html += '<li><a rel=' + item.id + ' href="#">环境:'
                    + item.name + '</a></li>';
            });
            $("#envChoice").html(html);
            $("#envChoice1").html(html);
            if (clean) {
                envId = -1;
            }
        }
    });
$("#envChoice").on('click', 'li a', function () {
    $("#envChoiceA span:first-child").text($(this).text());
    envId = $(this).attr('rel');
});
$("#envChoice1").on('click', 'li a', function () {
    $("#envChoiceA1 span:first-child").text($(this).text());
    copyEnvId = $(this).attr('rel');
});
