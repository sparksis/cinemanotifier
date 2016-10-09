angular.module("moviesApp").config(
		[ '$locationProvider', '$routeProvider',
				function config($locationProvider, $routeProvider) {
					$locationProvider.hashPrefix('!');

					$routeProvider.when('/movies/:movie', {
						templateUrl : 'movies.html',
						controllerAs : '$ctrl',
						controller : function($routeParams) {
							this.movie = $routeParams.movie
						}
					}).when('/', {
						templateUrl : 'default.html'
					});
				} ]);
