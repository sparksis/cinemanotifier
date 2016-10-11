angular.module('moviesApp', [ 'ngResource', 'ngRoute' ]);

angular.module('moviesApp').controller('MoviesController',
		function($scope, Movies) {
			$scope.movies = Movies.query();
		});