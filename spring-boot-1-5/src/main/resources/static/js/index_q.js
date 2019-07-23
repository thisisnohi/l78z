$(document).ready(function () {
    seat();//调用座位函数
    //隐藏域（座位）的值
    $("#seat-map").click(function () {
        var zw=$("#selected-seats").text();
        $("#hidden").attr("value",zw);
    });
    //选择新的结束时间
    $("#extension").click(function () {
        $(".form").show();
    })
    /*获取日期的处理+start*/
    //座位选择后的显示
    //显示日期下拉选框
    for(var i=0;i<3;i++){
        $(".cTimeDay>option:eq("+i+")").html(GetDateStr_0(i));
        $(".cTimeDay>option:eq("+i+")").attr("value",GetDateStr_1(i));//该语句为了便于下拉选中的数据的值
    }
    //这个是上述的对应函数
    //以下为日期下拉选择框自动调整
    function GetDateStr_0(i)//显示的值
    {
        var day = new Date();
        day.setDate(day.getDate()+i);//获取当天后一天的日期
        var m = day.getMonth()+1;
        var d = day.getDate();
        var r=null;//返回的日期字符串
        if (i==0){r="今天"+m+"月"+d+"日";}
        else if (i==1){r="明天"+m+"月"+d+"日";}
        else{r="后天"+m+"月"+d+"日";}
        return r;
    }
    function GetDateStr_1(i)//提交的值
    {
        var day = new Date();
        day.setDate(day.getDate()+i);//获取当天后一天的日期
        var m = day.getMonth()+1;
        var d = day.getDate();
        var r=null;//返回的日期字符串
        if (i==0){r=m+"/"+d;}
        else if (i==1){r=m+"/"+d;}
        else{r=m+"/"+d;}
        return r;
    }
});
//时间选择的判断
function check() {
    var Start=$(".cTimeStart option:selected");
    var StartTimeA=Start.text().split(":");
    var StartTime=parseInt(StartTimeA[0]);
    var Over=$(".cTimeOver option:selected");
    var OverTimeA=Over.text().split(":");
    var OverTime=parseInt(OverTimeA[0]);
    var x=StartTime>=OverTime;
    if (x)alert("非法的时间选择，请点击重置后，重新选择！");
}
function seat() {
    var map1= [  //座位图
        'aaaaaaaaaaaaaaaaaaaa' +
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa'+
        'aaaaaaaaaaaaaaaaaaaa',
    ];
    $("#circularG").show();
    $("#seat-map").hide();
    /*更新地图与新的预订活动*/
    // setInterval(function Data() {
    //     $.ajax({
    //         type     : 'post',
    //         url      : '../php/seat.php',
    //         dataType : 'json',
    //         success  : function(data) {
    //             $.each(data, function(index,data) {
    //                 $("#seat-map").show();
    //                 $("#circularG").hide();
    //                 sc.status(data.id, 'unavailable');
    //             });
    //         }
    //     });
    // }, 1000); //每10秒
    var $cart = $('#selected-seats');//座位区
    var sc = $('#seat-map').seatCharts({
        map: map1,
        naming: {
            top: false, //不显示顶部横坐标（行）
            left:false, //不显示顶部纵坐标（列）
        },
        legend : { //定义图例
            node : $('#legend'),
            items : [
                [ 'a', 'available',   '可选座' ],
                [ 'a', 'unavailable', '已被占'],
                [ 'a', 'selected', '已选中']
            ]
        },
        click: function () { //点击事件
            if (this.status() == 'available') { //可选座
                var row=this.settings.row+1;//图表的行
                var line=this.settings.label;//图表的列
                var ID=row*line;//选择座位的值
                $('<li>'+ID+'</li>')
                    .attr('id', 'cart-item-'+this.settings.id)
                    .data('seatId', this.settings.id)
                    .appendTo($cart);
                sc.find('available').status('unavailable');//学生选座全部禁用
                return 'selected';
            } else if (this.status() == 'selected') { //已选中
                //删除已预订座位
                $('#cart-item-'+this.settings.id).remove();
                sc.find('selected').status('available');//用户取消座位全部可用
                sc.find('unavailable').status('available');//用户取消座位全部可用
                seat();// 递归
                window.location.href ="index_q.html";
            } else if (this.status() == 'unavailable') { //已被选
                return 'unavailable';
            } else {
                return this.style();
            }
        }
    });
}