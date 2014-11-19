'use strict';

module.exports = {
    getHTML: function () {
        var data = {
            banner: __uri("img/banner.png"),
            letter: __uri("img/letter.jpg")
        };

        data.cc = 1;

        data.lasttime = 25;

        var time_inter = setInterval(function () {
            if (data.lasttime == 0) {
                clearInterval(time_inter);
            } else {
                data.lasttime = data.lasttime - 1;
            }
            cc = cc + 1;
        }, 1000);

        var tpl = __inline('letter.handlebars');
        return tpl(data);
    }
};