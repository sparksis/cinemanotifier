angular.module('moviesApp').factory('Users', function($resource) {
	return $resource('/rest/user/:email/:action');
});