package com.gracetee.meeterholic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gracetee.meeterholic.custom.CustomActivity;
import com.gracetee.meeterholic.utils.Utils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * The Class Register is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this Chat app.
 */
public class Register extends CustomActivity
{

	/** The username EditText. */
	private EditText user;

	/** The password EditText. */
	private EditText pwd;
    private static final int DEFAULT_MIN_PASSWORD_LENGTH = 8;

    /** The confirm password EditText. */
    private EditText confirmPwd;

	/** The email EditText. */
	private EditText email;

    /** The phoneNumber EditText. */
    private EditText phone;
    private static final String USER_OBJECT_PHONE = "phone";

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
        confirmPwd = (EditText) findViewById(R.id.confirmPwd);
		email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);

		String u = user.getText().toString();
		String p = pwd.getText().toString();
        String cP = confirmPwd.getText().toString();
		String e = email.getText().toString();
        String pn = phone.getText().toString();
		if (u.length() == 0 || p.length() == 0 || cP.length() == 0 || e.length() == 0 || pn.length() == 0)
		{
			Utils.showDialog(this, R.string.err_fields_empty);
			return;
		} else if (p.length() < DEFAULT_MIN_PASSWORD_LENGTH)
        {
            Utils.showDialog(this, R.string.err_password_too_short);
            return;
        } else if (!p.equals(cP)) {
            Utils.showDialog(this, R.string.err_confirm_password);
            confirmPwd.selectAll();
            confirmPwd.requestFocus();
        }

		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_wait));

		final ParseUser pu = new ParseUser();
		pu.setEmail(e);
		pu.setPassword(p);
		pu.setUsername(u);
        pu.put(USER_OBJECT_PHONE, pn);
		pu.signUpInBackground(new SignUpCallback() {

			@Override
			public void done(ParseException e)
			{
				dia.dismiss();
				if (e == null)
				{
					UserList.user = pu;
					startActivity(new Intent(Register.this, UserList.class));
					setResult(RESULT_OK);
					finish();
				}
				else
				{
                    String errMess = e.getMessage();
                    if (errMess.equals("PhoneDuplicate")){
                        Utils.showDialog(Register.this,R.string.err_phone_taken);
                    } else {
                        switch (e.getCode()) {
                            case ParseException.INVALID_EMAIL_ADDRESS:
                                Utils.showDialog(Register.this,R.string.err_invalid_email);
                                break;
                            case ParseException.USERNAME_TAKEN:
                                Utils.showDialog(Register.this,R.string.err_username_taken);
                                break;
                            case ParseException.EMAIL_TAKEN:
                                Utils.showDialog(Register.this,R.string.err_email_taken);
                                break;
                            default:
                                Utils.showDialog(Register.this,R.string.err_signup_failed_unknown);
                        }
                    }
					e.printStackTrace();
				}
			}
		});

	}
}
