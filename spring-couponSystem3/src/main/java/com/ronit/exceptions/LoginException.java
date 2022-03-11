package com.ronit.exceptions;

public class LoginException extends Exception{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String massage;

		public LoginException() {
			super("login error!");
		}

		public LoginException(String massage) {
			super(massage);
			this.massage = massage;
		}

	}


