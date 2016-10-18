angular.module('moviesApp').factory('Emails', function($resource) {
	return $resource('/rest/emails');
});