

(function (t, a, l, k, j, s) {
    s = a.createElement('script');
    s.async = 1;
    s.src = "https://cdn.talkjs.com/talk.js";
    a.head.appendChild(s)
    ;k = t.Promise;
    t.Talk = {
        v: 3, ready: {
            then: function (f) {
                if (k) return new k(function (r, e) {
                    l.push([f, r, e])
                });
                l
                    .push([f])
            }, catch: function () {
                return k && new k()
            }, c: l
        }
    };
})(window, document, []);

const dropdownMenu = document.querySelector(".dropdown-menu");
const dropdownButton = document.querySelector(".dropdown-toggle");

dropdownButton.addEventListener("click", () => {
    dropdownMenu.classList.toggle("hidden");
});