package com.example.cosplay_suit_app.Package_bill.Adapter;

import static com.example.cosplay_suit_app.Activity.BuynowActivity.formatDateTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cosplay_suit_app.DTO.BillDetailDTO;
import com.example.cosplay_suit_app.DTO.DTO_Bill;
import com.example.cosplay_suit_app.DTO.ItemImageDTO;
import com.example.cosplay_suit_app.R;
import com.example.cosplay_suit_app.bill.controller.Bill_controller;

import java.util.Date;
import java.util.List;

public class Adapter_Bill extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "adapterbill";
    List<BillDetailDTO> list;
    Context context;
    String checkactivity, checkstatus;

    public Adapter_Bill(List<BillDetailDTO> list, Context context, String checkactivity, String checkstatus) {
        this.list = list;
        this.context = context;
        this.checkactivity = checkactivity;
        this.checkstatus = checkstatus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BillDetailDTO billDetailDTO = list.get(position);
        Adapter_Bill.ItemViewHolder holder1 = (ItemViewHolder) holder;
        if (billDetailDTO.getDtoSanPham().getListImage() != null && !billDetailDTO.getDtoSanPham().getListImage().isEmpty()) {
            ItemImageDTO firstImage = billDetailDTO.getDtoSanPham().getListImage().get(0);
            String imageUrl = firstImage.getImage();

            // Tiến hành tải và hiển thị ảnh từ URL bằng Glide
            Glide.with(context)
                    .load(imageUrl)
                    .error(R.drawable.image)
                    .placeholder(R.drawable.image)
                    .centerCrop()
                    .into(holder1.imgproduct);
        }
        holder1.tvnamepro.setText(billDetailDTO.getDtoSanPham().getNameproduct());
        holder1.tvprice.setText(""+billDetailDTO.getDtoSanPham().getPrice());
        holder1.tvtongbill.setText(""+billDetailDTO.getDtoBill().getTotalPayment());
        holder1.tv_nameshop.setText(billDetailDTO.getDtoBill().getShop().getNameshop());
        Bill_controller billController = new Bill_controller(context);
        DTO_Bill dtoBill = new DTO_Bill();
        // Lấy đối tượng Date hiện tại
        Date currentDate = new Date();
        // Định dạng ngày giờ theo yyyyMMddHHmmss
        String formattedDateTime = formatDateTime(currentDate, "yyyyMMddHHmmss");
        if (checkstatus.equals("Wait")){
            billDetailDTO.getDtoBill().setStatus("Pack");
            dtoBill.setStatus("Pack");
        } else if (checkstatus.equals("Pack")) {
            dtoBill.setStatus("Delivery");
        }else if (checkstatus.equals("Delivery")) {
            dtoBill.setStatus("Done");
            dtoBill.setTimeend(formattedDateTime);
        }
        holder1.btn_upstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billController.UpdateStatusBill(billDetailDTO.getDtoBill().get_id(),dtoBill);
            }
        });
        if (checkactivity.equals("shop")){
            holder1.btn_upstatus.setVisibility(View.VISIBLE);
        }else {
            holder1.btn_upstatus.setVisibility(View.GONE);
        }
        if (billDetailDTO.getDtoBill().getStatus().equals("Wait")){
            holder1.btn_upstatus.setText(" Xác nhận có hàng ");
            holder1.tv_trangthai.setText("Đang xử lý");
        } else if (billDetailDTO.getDtoBill().getStatus().equals("Pack")) {
            holder1.btn_upstatus.setText(" Xác nhận Vận chuyển ");
            holder1.tv_trangthai.setText("Đang đóng gói");
        } else if (billDetailDTO.getDtoBill().getStatus().equals("Delivery")) {
            holder1.btn_upstatus.setText(" Giao hoàn tất ");
            holder1.tv_trangthai.setText("Đang giao");
        }else {
            holder1.btn_upstatus.setVisibility(View.GONE);
        }
        if (billDetailDTO.getDtoBill().getStatus().equals("Done")){
            holder1.btnmualai.setVisibility(View.VISIBLE);
        }else {
            holder1.btnmualai.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imgproduct;
        TextView tvnamepro, tvsize, tvprice, tvtongbill, tv_nameshop, tv_trangthai;
        Button btnmualai, btn_upstatus;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgproduct = itemView.findViewById(R.id.img_product);
            tvnamepro = itemView.findViewById(R.id.tvname_product);
            tvprice = itemView.findViewById(R.id.tvprice_product);
            tvsize = itemView.findViewById(R.id.tvsize_product);
            tvtongbill = itemView.findViewById(R.id.tv_tongbill);
            btnmualai = itemView.findViewById(R.id.btn_mualai);
            tv_nameshop = itemView.findViewById(R.id.tv_nameshop);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);
            btn_upstatus = itemView.findViewById(R.id.btn_upstatus);
        }
    }
}