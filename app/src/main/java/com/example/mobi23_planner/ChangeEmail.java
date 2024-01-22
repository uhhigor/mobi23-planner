package com.example.mobi23_planner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmail extends Fragment {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_email, container, false);

        EditText etCurrentEmail = view.findViewById(R.id.etCurrentEmail);
        etCurrentEmail.setText(user.getEmail());
        EditText etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        EditText etNewEmail = view.findViewById(R.id.etNewEmail);
        EditText etConfirmNewEmail = view.findViewById(R.id.etConfirmNewEmail);
        Button btnChangeEmail = view.findViewById(R.id.btnChangeEmail);

        btnChangeEmail.setOnClickListener(v -> {
            String currentEmail = user.getEmail();
            String currentPassword = etCurrentPassword.getText().toString();
            String newEmail = etNewEmail.getText().toString();
            String confirmNewEmail = etConfirmNewEmail.getText().toString();

            if (newEmail.equals(confirmNewEmail)) {
                changeEmail(currentEmail, newEmail , currentPassword);
            } else {
                Toast.makeText(requireContext(), "New email and confirmation do not match", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void changeEmail(String currentEmail, String newEmail , String currentPassword) {
        if (currentEmail.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, currentPassword);
        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.verifyBeforeUpdateEmail(newEmail)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            Toast.makeText(requireContext(), "Email changed successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(requireContext(), "Failed to change email", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(requireContext(), "Invalid current email", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}


