angular.module('emailSignup').component('emailSignup', {
	templateUrl : 'resources/js/email-signup/email-signup.template.html',
	controller : function($routeParams, Movies) {
		$self = this;
		$self.email = "";

		$self.movie = Movies.get({
			id : $routeParams.movie
		});

		$self.subscribe = function() {
			console.log($self.email)
		};
	}
});