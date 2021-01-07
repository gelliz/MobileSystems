package com.example.Lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ContactListFragment.OnItemSelected {

    private ArrayList<Contact> contactList;
    private Contact selectedContact;
    private FrameLayout contactInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isLandscapeMode()) {
            this.contactInfoFragment =
                    findViewById(R.id.contactInfoFragment);
        }

        contactList = MockedData.getMockedContacts();

        ContactListFragment contactListFragment = new ContactListFragment();
        Bundle bundle = new Bundle();

        bundle.putSerializable("contactList", this.contactList);
        contactListFragment.setArguments(bundle);

        this.getSupportFragmentManager().beginTransaction()
                .add(isLandscapeMode() ? R.id.contactListFragment : R.id.mainLayout, contactListFragment)
                .commit();
    }



    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            fragmentManager
                    .beginTransaction()
                    .commit();
        } else {
            Toast.makeText(this, "No previous fragment states found", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    @Override
    public void getSelectedContact(Contact contact) {
        this.selectedContact = contact;
        this.renderFragment(Constants.ShowInfo);
    }

    private void renderFragment(String fragmentName) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager()
                .beginTransaction();

        if (this.isLandscapeMode()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("contactInfo", this.selectedContact);

            ContactInfoFragment contactInfo = new ContactInfoFragment();
            contactInfo.setArguments(bundle);

            contactInfo.setArguments(bundle);
            fragmentTransaction
                    .replace(R.id.contactInfoFragment, contactInfo)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        switch (fragmentName) {
            case Constants.ShowList:
                fragmentTransaction.replace(
                        isLandscapeMode() ? R.id.contactListFragment : R.id.mainLayout,
                        new ContactListFragment()
                );
                break;
            case Constants.ShowInfo:
                Bundle bundle = new Bundle();
                bundle.putSerializable("contactInfo", this.selectedContact);

                ContactInfoFragment contactInfoFragment = new ContactInfoFragment();
                contactInfoFragment.setArguments(bundle);

                fragmentTransaction.replace(
                        isLandscapeMode() ? R.id.contactInfoFragment : R.id.mainLayout,
                        contactInfoFragment
                );
                break;
            default:
                break;
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean isLandscapeMode() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}