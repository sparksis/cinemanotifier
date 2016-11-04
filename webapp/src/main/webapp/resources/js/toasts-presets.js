angular.module('moviesApp').config(function($mdToastProvider) {
	window.presets=this;
	add = $mdToastProvider.addPreset;

	add('subscribeSuccess', {
		  options: function() {
			    return {
			      position: 'bottom right',
			      template:
			        '<md-toast>' +
			          '<div class="md-toast-content">' +
			            'You have successfully been subscribed {{email}}' +
			          '</div>' +
			        '</md-toast>',
			      controllerAs: 'MoviesController',
			      bindToController: true
			    };
		  }
	});
	
	add('subscribeFailure', {
		  options: function() {
			    return {
			      position: 'bottom right',
			      template:
			        '<md-toast>' +
			          '<div class="md-toast-content">' +
			            'Server refused to add your subscription (already subscribed?)' +
			          '</div>' +
			        '</md-toast>',
			      controllerAs: 'MoviesController',
			      bindToController: true
			    };
		  }
	});
});