
document.addEventListener('DOMContentLoaded', function () {

  function App() {
    let map = {
      all: 'PersonServlet?action=all',
      who: 'PersonServlet?action=who',
      what: 'PersonServlet?action=what',
      yes: 'PersonServlet?action=yes'
    };
    let base = url => axios.get(url).then(res => {
      /*console.log(res.data);*/return res.data;
    }, err => false);
    this.$all = () => base(map.all).then(data => data.users ? data : false, err => false);
    this.$who = () => base(map.who).then(data => data.who || false, err => false);
    this.$what = () => base(map.what).then(data => data.what || false, err => false);
    this.$yes = () => base(map.yes).then(data => data.who ? data : false, err => false);
    this.bubbly = () => {
      bubbly({
        colorStart: "#fff4e6", colorStop: "#ffe9e4", blur: 1, compose: "source-over",
        bubbleFunc: () => `hsla(${Math.random() * 50}, 100%, 50%, .3)`
      });
    };
    this.error = () => {
      swal({ title: "Emmm~！", icon: "error" });
    };
    this.init = () => {
      let temp = '';
      for (let i = 9; i >= 0; --i) {
        temp += '<div class="num"><span>' + i + '</span></div>';
      }temp += temp + temp + temp + temp;
      $('#scroller .item').each(function (i, dom) {
        if (i % 2) $(dom).css('background', '#f48fb1');
        dom.innerHTML = temp;
        $(dom).css('margin-top', -(6 * (5 * 10 - 1)) + 'em');
      });
    };
    this.scroll = (num, cb) => {
      var arr = num.toString().split(''),
          count = 0;
      $('#scroller .item').each(function (i, dom) {
        $(dom).css('margin-top', -(6 * 29) + 'em');
        setTimeout(function () {
          $(dom).animate({ marginTop: -6 * (9 - parseInt(arr[i])) + 'em' }, 3000, 'swing', function () {
            count++;
            if (count == 8) cb(num);
          });
        }, 500 * i);
      });
    };
  }

  (function (app) {
    let theGift,
        is = false;app.init();app.bubbly();
    let render = list => {
      $('#lister').empty();
      list.map((v, i) => {
        $('#lister').prepend(`<li class="${i % 2 ? 'sim' : ''}">
            <span class="id">${v.id}</span>
            <span class="name">${v.name}</span>
            <span class="times">${v.times}</span>
            <span class="count">${v.count}</span>
          </li>`);
      });
      $('#lister').prepend(`<li class="">
            <span class="id">ID</span>
            <span class="name">学名</span>
            <span class="times">变身次数</span>
            <span class="count">累计礼品</span>
          </li>`);
    };
    let loadList = data => {
      if (data) {
        render(data.users);
      } else {
        app.$all().then(data => {
          if (!data) return app.error();
          render(data.users);
        });
      }
    };

    let xe = {
      congGift: name => {
        swal({ title: "获得 " + name + " 大礼包~！" });
      },
      congPeople: (who, gift) => {
        swal({
          title: "钦定 " + who.name + " ，即将成为魔法少女！", text: "礼包：" + gift,
          buttons: {
            cancel: { text: "我觉得有失公正", visible: true, value: false },
            confirm: { text: "我觉得 OK", visible: true, value: true }
          }, dangerMode: false
        }).then(stt => {
          if (stt) {
            swal({ title: who.name + " 已签订契约~！", icon: "success" });
          } else {
            swal({ title: who.name + " 已被强制签订契约~！", icon: "warning" });
          }
          loadList();
        });
      }
    };

    app.$all().then(data => {
      if (!data) return app.error();
      let gifts = data.gifts;
      theGift = gifts[0].name;
      loadList(data);
      gbTurntable.init({ id: 'turntable',
        config: function (cb) {
          cb && cb(gifts.map(item => item.name));
        },
        getPrize: function (cb) {
          app.$what().then(what => {
            if (!what) return app.error();
            theGift = what.name;cb && cb([gifts.map(item => item.count).indexOf(what.count), 1024]);
          });
        }, gotBack: the => xe.congGift(theGift)
      });
    });
    $('#catch-people').click(function () {
      if (is) return;is = true;
      app.$who().then(function (who) {
        if (!who) {
          is = false;return app.error();
        }
        app.scroll(who.id || 15301159, num => {
          is = false;xe.congPeople(who, theGift);
        });
      }, function (err) {
        app.scroll(15301159);
      });
    });
  })(new App());
}, false);