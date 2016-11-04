angular.module('emailSignup').component('emailSignup', {
	templateUrl : 'resources/js/email-signup/email-signup.template.html',
	controller : function($routeParams, Movies, Users, $mdToast) {
		window.controller=$mdToast;
		$self = this;
		$self.email;

		$self.movie = Movies.get({
			id : $routeParams.movie
		});

		$self.subscribe = function() {
			Users.save(
				{email:$self.email, action:'subscribe'},
				[$self.movie.cineplexKey],
				function(){
					$mdToast.show($mdToast.subscribeSuccess());
				},
				function(){
					$mdToast.show($mdToast.subscribeFailure());
				}
			);
		};
	}
});
