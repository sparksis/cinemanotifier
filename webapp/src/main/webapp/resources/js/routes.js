angular.module("moviesApp").config(
		[ '$locationProvider', '$routeProvider',
				function config($locationProvider, $routeProvider) {
					$locationProvider.hashPrefix('!');
					$locationProvider.html5Mode(true);

					$routeProvider.when('/movies/:movie', {
						template : '<email-signup></email-signup>'
					}).when('/privacypolicy',{
						templateUrl : 'resources/pages/privacypolicy.html'
					}).when('/', {
						templateUrl : 'resources/pages/default.html'
					});
				} ]);
