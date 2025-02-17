package com.example.marketOrders.entity;

public enum StatusOrder {
    NEW(0, "Создан"),
    PENDING_PAYMENT(1, "Ожидает оплаты"),   // Если нужна оплата перед отправкой
    PROCESSING(2, "В обработке"),
    SHIPPED(3, "Отправлен"),               // Если есть доставка
    COMPLETED(4, "Завершен"),
    CANCELED(5, "Отменен");

    private final int code;
    private final String description;

    StatusOrder(int code, String descriprion) {
        this.code = code;
        this.description = descriprion;
    }

    public int getCode(){
        return code;
    }
    public String getDescription(){
        return description;
    }
}
