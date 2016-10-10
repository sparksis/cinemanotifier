angular.module("moviesApp").config(
		[ '$locationProvider', '$routeProvider',
				function config($locationProvider, $routeProvider) {
					$locationProvider.hashPrefix('!');

					$routeProvider.when('/movies/:movie', {
						templateUrl : 'resources/pages/movies.html',
						controllerAs : '$ctrl',
						controller : function($routeParams) {
							this.movie = $routeParams.movie
						}
					}).when('/', {
						templateUrl : 'resources/pages/default.html'
					});
				} ]);
