package com.example.exerciseapp.mAdapters;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseapp.R;
import com.example.exerciseapp.mEnums.ListType;
import com.example.exerciseapp.mEnums.NumberOfItem;
import com.example.exerciseapp.mEnums.RowNames;
import com.example.exerciseapp.mInterfaces.ISendUserData;
import com.example.exerciseapp.mInterfaces.IUserData;
import com.example.exerciseapp.mInterfaces.UpdateIntegersDB;
import com.example.exerciseapp.mInterfaces.UpdateStringsDB;
import com.example.exerciseapp.mModels.FourElementLinearListModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class FourElementLinearListAdapter extends RecyclerView.Adapter<FourElementLinearListAdapter.ViewHolder> {

    private Context mContext;


    private List<FourElementLinearListModel> list;
    private String listName;
    private static final String ZERO = "0";
    private static final String ONE = "1";
    private int oldPosition = 0;
    private char[] password;
    private ListType listType;
    private NumberOfItem numberOfItem;


    private UpdateIntegersDB valueDB;
    private UpdateStringsDB updateStringsDB;

    private ISendUserData iSendUserData;

    public FourElementLinearListAdapter(Context context, List<FourElementLinearListModel> list,
                                        String listName, UpdateIntegersDB valueDB,
                                        ListType listType, NumberOfItem numberOfItem) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.valueDB = valueDB;
        this.listType = listType;
        this.numberOfItem = numberOfItem;

        if (listType.equals(ListType.MULTIPLE_CHOICE_BUTTONS)) {
            for (int i = 0; i < list.size(); i++) {
                if (Integer.parseInt(list.get(i).getFirstValue()) == 1) {
                    oldPosition = list.get(i).getId();
                }
            }
        }
    }

    public FourElementLinearListAdapter(Context context, List<FourElementLinearListModel> list,
                                        String listName, UpdateStringsDB updateStringsDB,
                                        ListType listType, NumberOfItem numberOfItem) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.updateStringsDB = updateStringsDB;
        this.listType = listType;
        this.numberOfItem = numberOfItem;
    }

    public FourElementLinearListAdapter(Context context, List<FourElementLinearListModel> list,
                                        String listName, ISendUserData iSendUserData,
                                        ListType listType, NumberOfItem numberOfItem) {
        this.mContext = context;
        this.list = list;
        this.listName = listName;
        this.iSendUserData = iSendUserData;
        this.listType = listType;
        this.numberOfItem = numberOfItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.four_element_linear_block, parent, false);

        ViewHolder viewHolder = new ViewHolder(mView);

        mView.setOnClickListener(view -> {
            int pos = list.get(viewHolder.getBindingAdapterPosition()).getId();

            switch (listType) {
                case SELECTABLE_BUTTONS:
                    valueDB.values(listName, viewHolder.getBindingAdapterPosition(), pos,
                            list.get(viewHolder.getBindingAdapterPosition()).getSecondId());
                    break;
                case MULTIPLE_CHOICE_BUTTONS:
                    int temp;
                    if (list.get(viewHolder.getBindingAdapterPosition()).getFirstValue().equals(ZERO)) {
                        list.get(viewHolder.getBindingAdapterPosition()).setFirstValue(ONE);
                        temp = 1;
                    } else {
                        list.get(viewHolder.getBindingAdapterPosition()).setFirstValue(ZERO);
                        temp = 0;
                    }

                    valueDB.values(listName, list.get(viewHolder.getBindingAdapterPosition()).getId(),
                            temp, 0);
                    break;
                case SELECTABLE_BUTTONS_STR: {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle(list.get(viewHolder.getBindingAdapterPosition()).getName());
                    final View customLayout = LayoutInflater.from(mContext).inflate(
                            R.layout.alert_window, parent, false);
                    EditText editText = customLayout.findViewById(R.id.sAlertWindow_editText);
                    editText.setHint(R.string.enter_text);
                    alert.setView(customLayout);
                    alert.setPositiveButton(R.string.ok, (dialogInterface, i) -> {

                        if (listName.equals("tagTELL_account")) {
                            if (viewHolder.name.getText().equals(
                                    mContext.getString(R.string.password))) {
                                Editable editable = editText.getText();
                                password = new char[editable.length()];
                                for (int j = 0; j < editable.length(); j++) {
                                    password[j] = editable.charAt(j);
                                }
                                byte[] hash = hashPassword(password);

                                StringBuilder builder = new StringBuilder();
                                for (byte b : hash) {
                                    builder.append(String.format("%02x", b));
                                }
                                iSendUserData.sendData(listName,
                                        list.get(viewHolder.getBindingAdapterPosition()).getId(),
                                        RowNames.PASSWORD, builder.toString());
                            } else if (viewHolder.name.getText().equals(
                                    mContext.getString(R.string.username))) {
                                String value = editText.getText().toString();
                                iSendUserData.sendData(listName,
                                        list.get(viewHolder.getBindingAdapterPosition()).getId(),
                                        RowNames.NAME, value);
                            } else if (viewHolder.name.getText().equals(
                                    mContext.getString(R.string.e_mail))){
                                String value = editText.getText().toString();
                                iSendUserData.sendData(listName,
                                        list.get(viewHolder.getBindingAdapterPosition()).getId(),
                                        RowNames.EMAIL, value);
                            }
                        } else {
                            String value = editText.getText().toString();
                            updateStringsDB.strValues(listName, viewHolder.getBindingAdapterPosition(),
                                    list.get(viewHolder.getBindingAdapterPosition()).getId(),
                                    value);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss());
                    AlertDialog dialog = alert.create();
                    dialog.show();
                    break;
                }
                case SELECTABLE_BUTTONS_INT: {
                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    alert.setTitle(list.get(viewHolder.getBindingAdapterPosition()).getName());
                    final View customLayout = LayoutInflater.from(mContext).inflate(
                            R.layout.alert_window, parent, false);
                    EditText editText = customLayout.findViewById(R.id.sAlertWindow_editText);
                    editText.setHint(R.string.enter_number);
                    alert.setView(customLayout);
                    alert.setPositiveButton(R.string.update, (dialogInterface, i) -> {
                        if (!editText.getText().toString().equals("")) {
                            int value = Integer.parseInt(editText.getText().toString());
                            valueDB.values(listName, list.get(viewHolder.getBindingAdapterPosition()).getId(),
                                    value, 0);
                        }
                    });
                    alert.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss());
                    AlertDialog dialog = alert.create();
                    dialog.show();
                    break;
                }
                default:
                    valueDB.values(listName, oldPosition, pos, 0);
                    break;
            }
            notifyDataSetChanged();
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.icon.setImageResource(list.get(position).getIcon());
        holder.name.setText(list.get(position).getName());

        if (listType == ListType.MULTIPLE_CHOICE_BUTTONS) {
            if (list.get(position).getFirstValue().equals(ONE)) {
                holder.icon.setImageResource(R.drawable.ic_check_circle);
            } else {
                holder.icon.setImageResource(R.drawable.ic_uncheck_circle);
            }
        }

        if (numberOfItem.equals(NumberOfItem.ONE)) {
            holder.firstValue.setText(list.get(position).getFirstValue());
        } else if (numberOfItem.equals(NumberOfItem.TWO)) {
            holder.firstValue.setText(list.get(position).getFirstValue());
            holder.secondValue.setText(list.get(position).getSecondValue());
        } else if (numberOfItem.equals(NumberOfItem.NULL)) {
            Log.i(TAG, "onBindViewHolder: NULL not show TextView's");
        } else {
            holder.firstValue.setText(list.get(position).getFirstValue());
        }

        if (position == getItemCount() - 1) {
            holder.underline.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView icon;
        private final TextView name;
        private final TextView firstValue;
        private final TextView secondValue;
        private final View underline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.sFourElementLinearBlock_icon);
            name = itemView.findViewById(R.id.sFourElementLinearBlock_name);
            firstValue = itemView.findViewById(R.id.sFourElementLinearBlock_firstValue);
            secondValue = itemView.findViewById(R.id.sFourElementLinearBlock_secondValue);
            underline = itemView.findViewById(R.id.asset_four_elements_linear_block_underline);
        }
    }

    private static byte[] hashPassword(char[] password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            for (char c : password) {
                messageDigest.update((byte) c);
            }
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
