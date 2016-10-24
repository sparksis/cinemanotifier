angular.module('moviesApp', [ 'ngResource', 'ngRoute' , 'emailSignup', 'ngMaterial' ]);

angular.module('moviesApp').controller('MoviesController',
		function($scope, Movies) {
			$scope.movies = Movies.query();
		});