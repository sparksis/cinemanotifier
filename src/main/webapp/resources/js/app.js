angular.module('moviesApp', [ 'ngResource', 'ngRoute' ]);

angular.module('moviesApp').factory('Movies', function($resource) {
	return $resource('/rest/movies');
});

angular.module('moviesApp').controller('MoviesController',
		function($scope, Movies) {
			$scope.movies = Movies.query();
		});