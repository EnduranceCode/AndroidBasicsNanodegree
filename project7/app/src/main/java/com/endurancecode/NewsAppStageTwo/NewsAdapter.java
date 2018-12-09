package com.endurancecode.NewsAppStageTwo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * {@link NewsAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
 * based on a data source, which is a list of {@link News} objects.
 * Since this adapter is used to convert the data source of type News to a view, we can avoid
 * explicit casting by having the class extend ArrayAdapter<News>
 */
public class NewsAdapter extends ArrayAdapter<News> {
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context the current context. Used to inflate the layout file.
     * @param news    a List of {@link News} objects to display in a list
     */
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    /**
     * Load the ListView with the {@link News} objects data
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /* Check if the existing view is being reused, otherwise inflate the view */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_news, parent, false);
        }

        /* Get the {@link News} object located at this position in the list */
        News currentNews = getItem(position);

        /* Find the TextView for the section name and set it with its data */
        TextView sectionTextView = convertView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getSectionName());

        /* Find the TextView for the day of the web publication date and set it with its data */
        TextView dayTextView = convertView.findViewById(R.id.day);
        dayTextView.setText(currentNews.getDay());

        /* If day is null we set its TextView background color with thw same color of the month TextView */
        if (currentNews.getDay() == null) {
            dayTextView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        /* Find the TextView for the section name and set it with its data */
        TextView monthTextView = convertView.findViewById(R.id.month);
        monthTextView.setText(currentNews.getMonth());

        /* Find the TextView for the section name and set it with its data */
        TextView webTitleTextView = convertView.findViewById(R.id.web_title);
        webTitleTextView.setText(currentNews.getWebTitle());

        /* Find the TextView for the section name and set it with its data */
        TextView bylineTextView = convertView.findViewById(R.id.byline);
        bylineTextView.setText(currentNews.getByline());

        /* Return the whole list item layout so that it can be shown in the ListView */
        return convertView;
    }
}
