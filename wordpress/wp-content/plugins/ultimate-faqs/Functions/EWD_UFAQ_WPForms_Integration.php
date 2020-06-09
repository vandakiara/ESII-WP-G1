<?php

if ( get_option( 'EWD_UFAQ_WPForms_Integration' ) == 'Yes' ) {
	global $target_field;

	add_filter( 'wpforms_builder_settings_sections', 'EWD_UFAQ_WPForms_Add_Settings_Panel' );
	add_action( 'wpforms_form_settings_panel_content', 'EWD_UFAQ_WPForms_Add_Settings' );

	add_action( 'wpforms_frontend_output_before', 'EWD_UFAQ_WPForms_Integration_Localization', 10, 2 );

	if ( get_option( 'EWD_UFAQ_WPForms_FAQ_Location' ) == 'Below' ) { add_action( 'wpforms_display_field_after', 'EWD_UFAQ_WPForms_Integration', 10, 2 ); }
	else { add_action( 'wpforms_display_field_before', 'EWD_UFAQ_WPForms_Integration', 10, 2 ); }
}

function EWD_UFAQ_WPForms_Add_Settings_Panel( $panels ) {
	$panels['ufaq'] = esc_html__( 'FAQs', 'wpforms-lite' );

	return $panels;
}

function EWD_UFAQ_WPForms_Add_Settings( $form ) {
	echo '<div class="wpforms-panel-content-section wpforms-panel-content-section-ufaq">';

		echo '<div class="wpforms-panel-content-section-title">';
			esc_html_e( 'Ultimate FAQs', 'wpforms-lite' );
		echo '</div>';

		wpforms_panel_field(
			'radio',
			'settings',
			'ufaq_enabled',
			$form->form_data,
			esc_html__( 'Disable FAQ display for this form, or enable it only on specific fields.', 'wpforms-lite' ),
			array(
				'options' => array(
					'enabled' => array( 'label' => 'Enable' ),
					'disabled' => array( 'label' => 'Disable' ),
					'specific' => array( 'label' => 'Specific Field' )
				)
			)
		);

		wpforms_panel_field(
			'select',
			'settings',
			'ufaq_selected_field',
			$form->form_data,
			esc_html__( 'If FAQs are set to a specific field, which field should FAQs be displayed for?', 'wpforms-lite' ),
			array(
				'field_map' => array(
					'text',
					'textarea'
				)
			)
		);

	echo '</div>';
}

function EWD_UFAQ_WPForms_Integration_Localization( $form_data, $form ) {
	global $target_field;

	if ( isset ( $form_data['settings']['ufaq_enabled'] ) and $form_data['settings']['ufaq_enabled'] == 'specific' ) {
		$target_field = isset( $form_data['settings']['ufaq_selected_field'] ) ? $form_data['settings']['ufaq_selected_field'] : 0;
	}
	elseif ( isset ( $form_data['settings']['ufaq_enabled'] ) and $form_data['settings']['ufaq_enabled'] != 'disabled' ) {
		foreach ( $form_data['fields'] as $field_id => $field ){
			if ( $field['type'] == 'textarea' ) {
				$target_field = $field['id'];
				break;
			}
		}
	}
	
	wp_localize_script( 
		'ewd-ufaq-js', 
		'wpforms_integration', 
		array(
			'ufaq_enabled' => isset ( $form_data['settings']['ufaq_enabled'] ) ? $form_data['settings']['ufaq_enabled'] : 'enabled',
			'ufaq_selected_field' => $target_field,
			'form_id' => $form_data['id']
		)
	);
}

function EWD_UFAQ_WPForms_Integration( $field, $form_data ) {
	global $target_field;

	if ( isset( $form_data['settings']['ufaq_enabled'] ) and $form_data['settings']['ufaq_enabled'] != 'disabled' and $field['id'] == $target_field ) {
		echo '<div class="ewd-ufaq-wpforms-label ewd-ufaq-hidden">Possible FAQs related to your message:</div>';
		echo "<input type='hidden' name='current_url' value='" . $_SERVER['REQUEST_URI'] . "' id='ufaq-current-url' />";
		echo do_shortcode( '[ultimate-faq-search wp_forms_load="Yes"]' );
	}
}

?>