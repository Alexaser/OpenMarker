-- Создаем таблицу SKU
CREATE TABLE SKU (
                     ID INT AUTO_INCREMENT PRIMARY KEY,  -- Используем AUTO_INCREMENT для MySQL
                     Code CHAR(10) UNIQUE,  -- Убираем вычисляемое поле
                     Name NVARCHAR(255) NOT NULL
);

-- Создаем триггер для автоматического обновления поля Code
DELIMITER $$

CREATE TRIGGER after_sku_insert
    AFTER INSERT ON SKU
    FOR EACH ROW
BEGIN
    UPDATE SKU
    SET Code = CONCAT('s', LPAD(NEW.ID, 10, '0'))
    WHERE ID = NEW.ID;
END $$

DELIMITER ;
-- Создаем таблицу Family
CREATE TABLE Family (
                            ID INT AUTO_INCREMENT PRIMARY KEY,  -- Используем AUTO_INCREMENT для MySQL
                            SurName NVARCHAR(255) NOT NULL,
                            BudgetValue DECIMAL(18, 2) NOT NULL
);

-- Создаем таблицу Basket
CREATE TABLE Basket (
                        ID INT AUTO_INCREMENT PRIMARY KEY,  -- Используем AUTO_INCREMENT для MySQL
                        ID_SKU INT NOT NULL,
                        ID_Family INT NOT NULL,
                        Quantity INT CHECK (Quantity >= 0) NOT NULL,   -- Ограничение на положительное значение
                        Value DECIMAL(18, 2) CHECK (Value >= 0) NOT NULL, -- Ограничение на положительное значение
                        PurchaseDate DATE,   -- Убираем DEFAULT CURDATE()
                        DiscountValue DECIMAL(18, 2) CHECK (DiscountValue >= 0), -- Ограничение на DiscountValue
                        FOREIGN KEY (ID_SKU) REFERENCES SKU(ID),
                        FOREIGN KEY (ID_Family) REFERENCES Family(ID)
);

DELIMITER $$

CREATE TRIGGER before_insert_basket
    BEFORE INSERT ON Basket
    FOR EACH ROW
BEGIN
    IF NEW.PurchaseDate IS NULL THEN
        SET NEW.PurchaseDate = CURDATE();  -- Устанавливаем текущую дату, если значение не передано
    END IF;
END $$

DELIMITER ;