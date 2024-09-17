package ru.petflower.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@UtilityClass
public class DeviceNameGenerator {

    private final List<String> adjectives = Arrays.asList(
            "Веселый", "Забавный", "Хитрый", "Смелый", "Ленивый", "Шустрый", "Загадочный", "Мудрый",
            "Зловредный", "Пушистый", "Грозный", "Дружелюбный", "Неуклюжий", "Радостный", "Храбрый",
            "Сонный", "Светящийся", "Чудесный", "Упрямый", "Коварный", "Заботливый", "Тихий", "Шумный"
    );

    private final List<String> nouns = Arrays.asList(
            "Кот", "Лев", "Дракон", "Робот", "Енот", "Кролик", "Медведь", "Ежик",
            "Лис", "Слон", "Пингвин", "Котопес", "Бобр", "Жираф", "Тигр", "Волк",
            "Панда", "Осьминог", "Носорог", "Обезьяна", "Мышонок", "Барсук", "Птеродактиль", "Кенгуру"
    );


    public String generateDeviceName() {
        Random random = new Random();
        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String noun = nouns.get(random.nextInt(nouns.size()));
        return adjective + " " + noun;
    }

}
