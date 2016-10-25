angular.module('moviesApp', [ 'ngResource', 'ngRoute' , 'emailSignup', 'ngMaterial' ]);

angular.module('moviesApp').controller('MoviesController',
		function(Movies) {
			this.movies = Movies.query();
		}
);