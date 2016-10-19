/**
 * REST Resource to handle movies from the server
 */
angular.module('moviesApp').factory('Movies', function($resource) {
	return $resource('/rest/movies');
});