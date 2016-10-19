angular.module('moviesApp', [ 'ngResource', 'ngRoute' , 'emailSignup' ]);

angular.module('moviesApp').controller('MoviesController',
		function($scope, Movies) {
			$scope.movies = Movies.query();
		});