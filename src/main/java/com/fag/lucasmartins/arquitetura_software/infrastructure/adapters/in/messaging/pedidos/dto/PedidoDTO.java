package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

    private String zipCode;
    private Integer customerId;
    private List<OrderItemDTO> orderItems;
    private String origin;
    private LocalDateTime occurredAt; 

    public static class OrderItemDTO {
        private Integer sku;
        private Integer amount;

        public Integer getSku() { return sku; }
        public void setSku(Integer sku) { this.sku = sku; }

        public Integer getAmount() { return amount; }
        public void setAmount(Integer amount) { this.amount = amount; }
    }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}