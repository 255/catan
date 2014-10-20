package client.login;

import client.base.*;
import client.misc.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.network.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {
    private final static Logger logger = Logger.getLogger("catan");

	private IMessageView messageView;
	private IAction loginAction;
    private IGameAdministrator m_admin;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;

        m_admin = GameAdministrator.getInstance();
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() {
        String username = getLoginView().getLoginUsername();
        String password = getLoginView().getLoginPassword();

        boolean success = false;
        try {
            success = m_admin.login(username, password);
        } catch (NetworkException e) {
            logger.log(Level.WARNING, "Sign in failed.", e);
        }

		// If log in succeeded
        if (success) {
            getLoginView().closeModal();
            loginAction.execute();
        } else {
            getMessageView().showModal();
            getMessageView().setTitle("Error!");
            getMessageView().setMessage("Sign in failed.");
        }
	}

	@Override
	public void register() {
        String username = getLoginView().getRegisterUsername();
        String password = getLoginView().getRegisterPassword();
        String passwordRepeat = getLoginView().getRegisterPasswordRepeat();

        if (password.equals(passwordRepeat)) {
            boolean success = false;
            try {
                success = m_admin.register(username, password);
            } catch (NetworkException e) {
                logger.log(Level.WARNING, "Register failed.", e);
            }

            // If register succeeded
            if (success) {
                getLoginView().closeModal();
                loginAction.execute();
            } else {
                getMessageView().showModal();
                getMessageView().setTitle("Error!");
                getMessageView().setMessage("Register failed.");
            }
        } else {
            getMessageView().showModal();
            getMessageView().setTitle("Warning!");
            getMessageView().setMessage("Passwords don't match.");
        }
	}

    @Override
    public void update(Observable o, Object arg) {

    }
}

