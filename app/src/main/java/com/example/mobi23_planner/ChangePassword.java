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

public class ChangePassword extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_password, container, false);

        EditText etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        EditText etNewPassword = view.findViewById(R.id.etNewPassword);
        EditText etConfirmNewPassword = view.findViewById(R.id.etConfirmNewPassword);
        Button btnChangePassword = view.findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(v -> {
            String currentPassword = etCurrentPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();
            String confirmNewPassword = etConfirmNewPassword.getText().toString();

            if (newPassword.equals(confirmNewPassword)) {
                changePassword(currentPassword, newPassword);
            } else {
                Toast.makeText(requireContext(), "New password and confirmation do not match", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void changePassword(String currentPassword, String newPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword)
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to change password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(requireContext(), "Invalid current password", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


