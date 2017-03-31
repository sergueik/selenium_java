/******************************************
 *                                        *
 * Auth: green gerong                     *
 * Date: 2014.                         *
 * blog: http://greengerong.github.io/    *
 * github: https://github.com/greengerong *
 *                                        *
 ******************************************/

var AngularHomepage = function () {
    this.nameInput = element(by.model('yourName'));
    this.greeting = element(by.binding('yourName'));

    this.get = function () {
        browser.get('http://www.angularjs.org');
    };

    this.setName = function (name) {
        this.nameInput.sendKeys(name);
    };
};

describe('angularjs homepage', function () {
    it('should greet the named user', function () {
        var angularHomepage = new AngularHomepage();
        angularHomepage.get();

        angularHomepage.setName('Julie');

        expect(angularHomepage.greeting.getText()).toEqual('Hello Julie!');
    });
});