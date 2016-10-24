angular.module('emailSignup').component('emailSignup', {
	templateUrl : 'resources/js/email-signup/email-signup.template.html',
	controller : function($routeParams,Movies) {
		this.movie = Movies.get({id:$routeParams.movie});
	}
});