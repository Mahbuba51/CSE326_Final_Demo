package com.foodibd.backend.seeder;

import com.foodibd.backend.entity.*;
import com.foodibd.backend.entity.embedded.Address;
import com.foodibd.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        if (restaurantRepository.count() > 0) {
            log.info("Database already seeded — skipping.");
            return;
        }

        log.info("Seeding database...");

        // ── Restaurant ────────────────────────────────────────────────────────
        Restaurant restaurant = restaurantRepository.save(
                Restaurant.builder()
                        .name("Chillox - Lalbagh")
                        .cuisineTypes(List.of("Burger", "Fast Food"))
                        .rating(3.9)
                        .available(true)
                        .address(new Address(
                                "Lalbagh Road 12",
                                "Dhaka",
                                "Dhaka Division",
                                "1211",
                                "Bangladesh"))
                        .build()
        );

        // ── Menu ──────────────────────────────────────────────────────────────
        Menu menu = menuRepository.save(
                Menu.builder()
                        .restaurant(restaurant)
                        .build()
        );

        // ── Menu items ────────────────────────────────────────────────────────

        // 1. Classic Chicken Burger
        MenuItem chickenBurger = menuItemRepository.save(
                MenuItem.builder()
                        .name("Classic Chicken Burger")
                        .description("Juicy chicken patty with cheese, lettuce, tomato")
                        .price(188.0)
                        .availability(true)
                        .isPopular(true)
                        .thumbnailUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&h=300&fit=crop")
                        .categories(List.of("Burgers"))
                        .menu(menu)
                        .build()
        );
        seedChickenBurgerCustomizationsAndAddons(chickenBurger);

        // 2. Fish Burger
        MenuItem fishBurger = menuItemRepository.save(
                MenuItem.builder()
                        .name("Fish Burger")
                        .description("Crispy fish fillet with tartar sauce")
                        .price(220.0)
                        .availability(true)
                        .isPopular(true)
                        .thumbnailUrl("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400&h=300&fit=crop")
                        .categories(List.of("Burgers"))
                        .menu(menu)
                        .build()
        );
        seedFishBurgerCustomizationsAndAddons(fishBurger);

        // 3. Spicy Wings Combo
        MenuItem wingsCombo = menuItemRepository.save(
                MenuItem.builder()
                        .name("Spicy Wings Combo")
                        .description("6 spicy wings with dipping sauce and fries")
                        .price(420.0)
                        .availability(true)
                        .isPopular(false)
                        .thumbnailUrl("https://images.unsplash.com/photo-1527477396000-e27163b481c2?w=400&h=300&fit=crop")
                        .categories(List.of("Combos"))
                        .menu(menu)
                        .build()
        );
        seedWingsComboCustomizations(wingsCombo);

        // 4. Cheese Fries
        MenuItem cheeseFries = menuItemRepository.save(
                MenuItem.builder()
                        .name("Cheese Fries")
                        .description("Golden fries loaded with melted cheese")
                        .price(180.0)
                        .availability(true)
                        .isPopular(false)
                        .thumbnailUrl("https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=400&h=300&fit=crop")
                        .categories(List.of("Sides"))
                        .menu(menu)
                        .build()
        );
        seedSizeCustomization(cheeseFries, 30);

        // 5. Onion Rings
        menuItemRepository.save(
                MenuItem.builder()
                        .name("Onion Rings")
                        .description("Crispy golden onion rings")
                        .price(150.0)
                        .availability(true)
                        .isPopular(false)
                        .thumbnailUrl("https://images.unsplash.com/photo-1639024471283-03518883512d?w=400&h=300&fit=crop")
                        .categories(List.of("Sides"))
                        .menu(menu)
                        .build()
        );

        // 6. Chocolate Shake
        MenuItem shake = menuItemRepository.save(
                MenuItem.builder()
                        .name("Chocolate Shake")
                        .description("Rich and creamy chocolate milkshake")
                        .price(220.0)
                        .availability(true)
                        .isPopular(false)
                        .thumbnailUrl("https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400&h=300&fit=crop")
                        .categories(List.of("Drinks"))
                        .menu(menu)
                        .build()
        );
        seedSizeCustomization(shake, 40);

        // ── Demo customer ─────────────────────────────────────────────────────
        customerRepository.save(
                Customer.builder()
                        .name("Demo User")
                        .email("demo@foodi.bd")
                        .phoneNumber("01700000000")
                        .address(new Address(
                                "Road 5, House 12",
                                "Dhaka",
                                "Dhaka Division",
                                "1209",
                                "Bangladesh"))
                        .build()
        );

        log.info("Seeding complete.");
    }

    // ── Per-item seed helpers ─────────────────────────────────────────────────

    private void seedChickenBurgerCustomizationsAndAddons(MenuItem item) {
        // Bun choice
        Customization bun = saveCustomization("Choose Your Bun", item);
        saveOption("Brioche Bun", 0, true,  bun);
        saveOption("Sesame Bun",  0, false, bun);

        // Spice level
        Customization spice = saveCustomization("Spice Level", item);
        saveOption("Mild",         0, true,  spice);
        saveOption("Extreme Naga", 0, false, spice);

        // Toppings as Addons
        saveAddon("BBQ Sauce",     28, false, item);
        saveAddon("Egg",           28, false, item);
        saveAddon("Beef Bacon",    72, true,  item);
        saveAddon("Honey BBQ",     32, false, item);
        saveAddon("Sausage",       48, false, item);
        saveAddon("Chicken Patty", 120, true, item);
    }

    private void seedFishBurgerCustomizationsAndAddons(MenuItem item) {
        Customization spice = saveCustomization("Spice Level", item);
        saveOption("Mild", 0, true,  spice);
        saveOption("Hot",  0, false, spice);

        saveAddon("Extra Cheese", 32, false, item);
        saveAddon("Jalapeños",    24, false, item);
    }

    private void seedWingsComboCustomizations(MenuItem item) {
        Customization spice = saveCustomization("Spice Level", item);
        saveOption("Mild",       0, false, spice);
        saveOption("Hot",        0, true,  spice);
        saveOption("Extra Hot",  0, false, spice);
    }

    private void seedSizeCustomization(MenuItem item, int largeExtraPrice) {
        Customization size = saveCustomization("Size", item);
        saveOption("Regular", 0,               true,  size);
        saveOption("Large",   largeExtraPrice, false, size);
    }

    // ── Low-level save helpers ────────────────────────────────────────────────

    private Customization saveCustomization(String description, MenuItem item) {
        Customization c = new Customization();
        c.setDescription(description);
        c.setMenuItem(item);
        // We save via the item's cascade, but since item is already persisted
        // we need to use a direct save. Inject repo via field.
        return customizationRepository.save(c);
    }

    private void saveOption(String name, int extraPrice, boolean isDefault, Customization c) {
        CustomizationOption opt = new CustomizationOption();
        opt.setOptionName(name);
        opt.setExtraPrice(extraPrice);
        opt.setIsDefault(isDefault);
        opt.setCustomization(c);
        customizationOptionRepository.save(opt);
    }

    private void saveAddon(String name, int price, boolean isPopular, MenuItem item) {
        Addon addon = new Addon();
        addon.setName(name);
        addon.setPrice(price);
        addon.setIsPopular(isPopular);
        addon.setMenuItem(item);
        addonRepository.save(addon);
    }

    // Additional repos needed for seeding
    private final com.foodibd.backend.repository.CustomizationRepository customizationRepository;
    private final com.foodibd.backend.repository.AddonRepository addonRepository;
    private final com.foodibd.backend.repository.CustomizationOptionRepository customizationOptionRepository;
}
