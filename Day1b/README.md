# Day 1b – Inventory / Cart System

## Problem Statement

Design a minimal e-commerce inventory system where:

- An **Admin** creates product listings (e.g. "Usha Fan") in the catalog.
- An Admin adds **physical items** (one per unit, each with its own serial number) to a **Warehouse**.
- A **User** has a **Cart** and can reserve a specific physical item from the warehouse into it.
- Each physical item has a **status** (`AVAILABLE`, `IN_CART`, `SOLD`); reserving an item makes it unavailable to others.
- The warehouse can report the available stock count for a product.

## Solution

| File | Responsibility |
|------|----------------|
| [Product.java](Product.java) | Catalog item: id, name, price and the admin who created it. |
| [SubProduct.java](SubProduct.java) | A single physical unit of a product with a serial number and `ItemStatus`. |
| [Warehouse.java](Warehouse.java) | Holds inventory per product id; counts available stock and reserves an available item. |
| [Admin.java](Admin.java) | Creates product listings and adds N physical items to the warehouse. |
| [User.java](User.java) | A customer; owns a one-to-one `Cart`. |
| [Cart.java](Cart.java) | Maps each `Product` to the specific `SubProduct`s reserved by the user. |
| [Main.java](Main.java) | Demo: admin lists an Usha Fan and stocks 5 units, Alice reserves one into her cart. |

### Flow

1. `Admin.createProductListing(...)` creates a `Product`.
2. `Admin.addItemsToWarehouse(...)` generates `SubProduct`s (status `AVAILABLE`) and calls `Warehouse.addStock(...)`.
3. `Warehouse.reserveAvailableItem(productId)` returns the first `AVAILABLE` item and flips it to `IN_CART` (or `null` if out of stock).
4. The reserved item is added to the user's cart via `Cart.addItem(...)`.
5. `Warehouse.getAvailableStockCount(productId)` now reports one fewer unit.

## Design Notes

- **Product vs SubProduct** separates the catalog concept (what is sold) from the physical stock (what is on the shelf).
- Reservation is not thread-safe yet; concurrent users could grab the same item. Making `reserveAvailableItem` synchronized (or using per-item CAS on status) is a natural next step.
- `SubProduct` ids use `System.nanoTime()` for simplicity; a proper ID generator would replace it.

## Run

From the repo root (files use `package Day1b;`):

```bash
javac Day1b/*.java
java Day1b.Main
```

Expected output:

```
--- Admin Actions ---
Product Created: 1
Initial Available Stock for Usha Fan: 5

--- User Actions ---
User Alice is browsing...
Successfully reserved physical item with Serial: SN-<id>
Item added to Alice's cart.

--- System Status ---
Remaining Available Stock for Usha Fan: 4
```
